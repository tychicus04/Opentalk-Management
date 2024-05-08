package com.tychicus.opentalk.service.impl;

import com.cloudinary.Cloudinary;
import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.employee.EmployeeDTOForOpenTalk;
import com.tychicus.opentalk.dto.opentalk.OpenTalkCount;
import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import com.tychicus.opentalk.event.ListenSendEmail;
import com.tychicus.opentalk.exception.BadInputException;
import com.tychicus.opentalk.exception.ItemsNotFoundException;
import com.tychicus.opentalk.mapper.OpenTalkMapper;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import com.tychicus.opentalk.repository.CompanyBranchRepository;
import com.tychicus.opentalk.repository.EmployeeRepository;
import com.tychicus.opentalk.repository.OpenTalkIsHostedRepository;
import com.tychicus.opentalk.repository.OpenTalkRepository;
import com.tychicus.opentalk.service.IOpenTalkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OpenTalkService implements IOpenTalkService {
    @Autowired
    private OpenTalkRepository openTalkRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyBranchRepository companyBranchRepository;
    @Autowired
    private OpenTalkMapper openTalkMapper;
    @Autowired
    private OpenTalkIsHostedRepository openTalkIsHostedRepository;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CloudinaryService cloudinaryService;

    // get all open talks
    @Override
    public List<OpenTalkDTO> getAllOpenTalks() {
        // Try to get the list from Redis first
//        List<OpenTalkDTO> openTalkDTOs = (List<OpenTalkDTO>) redisService.get("allOpenTalks");
//        if (openTalkDTOs == null) {
            // If the list is not in Redis, get it from the database
            List<OpenTalk> openTalks = openTalkRepository.findAll().stream()
                    .sorted(Comparator.comparing(OpenTalk::getStartTime))
                    .collect(Collectors.toList());
            // convert entity list to dto list
        List<OpenTalkDTO> openTalkDTOs = openTalkMapper.entityListToDtoList(openTalks);
            // Then store it in Redis for future use
//            redisService.setObject("allOpenTalks", openTalkDTOs);
//        }
        return openTalkDTOs;
    }

    // get open talk by id
    @Override
    public OpenTalkDTO getOpenTalkById(Long openTalkId) {
        // get open talk by id from repository and throw exception if not found
        Optional<OpenTalk> theOpenTalk = openTalkRepository.findById(openTalkId);
        if (theOpenTalk.isEmpty()) {
            throw new ItemsNotFoundException("Open Talk not found with id: " + openTalkId);
        }
        // convert entity to dto and return
        return openTalkMapper.entityToDto(theOpenTalk.get());
    }

    // create/update open talk
    @Override
    @CacheEvict(value = "openTalks", allEntries = true)
    public OpenTalkDTO saveOpenTalk(OpenTalkDTO openTalkDTO) {
        // convert DTO to entity
        OpenTalk openTalk = openTalkMapper.dtoToEntity(openTalkDTO);
        Set<Employee> participants = new HashSet<>();
        // set host from dto if available else set to null
        if (openTalkDTO.getHost() != null) {
            // get host by id from repository and throw exception if not found
            Optional<Employee> optionalEmployee = employeeRepository.findById(openTalkDTO.getHost().getId());
            if (optionalEmployee.isEmpty()) {
                throw new BadInputException("Could not find any user with id : "+openTalkDTO.getHost().getId() +
                        " for host this open talk");
            } else {
                // set host to open talk
                Employee employee = optionalEmployee.get();
                openTalk.setHost(employee);
                // get OpenTalkIsHosted by open talk id from repository
                OpenTalkIsHosted openTalkIsHosted = openTalkIsHostedRepository.findByIdOpenTalk(openTalk.getId());
                // update OpenTalk case (exist OpenTalkIsHosted), find it in DB set user again else create new OpenTalkIsHosted and add host to it
                if (openTalkIsHosted != null) {
                    openTalkIsHosted.setEmployee(employee);
                    openTalkIsHostedRepository.save(openTalkIsHosted);
                } else {
                    // create OpenTalk case (or not exist OpenTalkIsHosted)
                    // after set idOpenTalk For OpenTalkIsHosted again, because create new OpenTalk, id = 0
                    OpenTalkIsHosted newOpenTalkIsHosted = openTalkMapper.openTalkToOpenTalkIsHost(openTalk);
                    newOpenTalkIsHosted.setId(0L);
                    newOpenTalkIsHosted.setEmployee(employee);
                    openTalkIsHostedRepository.save(newOpenTalkIsHosted);
                }
                // add to participants
                participants.add(employee);
            }
        } else {
            openTalk.setHost(null);
        }

        // set slide string
        if (openTalkDTO.getSlide() != null) {
            openTalk.setSlide(openTalkDTO.getSlide());
        } else {
            openTalk.setSlide(null);
        }

        // set company branch
        if (openTalkDTO.getCompanyBranchName() != null) {
            Optional<CompanyBranch> companyBranchOptional
                    = companyBranchRepository.findByName(openTalkDTO.getCompanyBranchName());
            if (companyBranchOptional.isEmpty()) {
                throw new BadInputException("Can not find company branch: "
                        + openTalkDTO.getCompanyBranchName());
            }
            openTalk.setCompanyBranch(companyBranchOptional.get());
        } else {
            openTalk.setCompanyBranch(null);
        }

        // Get employees joined open talk before modify (in case : update)
        Set<Employee> employeesJoinOpenTalkBefore = new HashSet<>();
        if (openTalkDTO.getId() != 0L) {
            OpenTalk openTalk1 = openTalkRepository.findById(openTalk.getId()).get();
            employeesJoinOpenTalkBefore = openTalk1.getParticipants();
        }

        // set participants from dto
        if (openTalkDTO.getParticipants() != null) {
            for (EmployeeDTOForOpenTalk employee : openTalkDTO.getParticipants()) {
                Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
                if (optionalEmployee.isEmpty()) {
                    throw new BadInputException("Could not find any user with id : "+employee.getId() +
                            " for join this open talk");
                }
                participants.add(optionalEmployee.get());
            }
        }
        openTalk.setParticipants(participants);

        // Get all user in before modify and after modify user in set open talk
        Set<Employee> mergeEmployeeJoinedOpenTalk = new HashSet<>();
        mergeEmployeeJoinedOpenTalk.addAll(employeesJoinOpenTalkBefore);
        mergeEmployeeJoinedOpenTalk.addAll(participants);

        OpenTalk savedOpenTalk = openTalkRepository.save(openTalk);

        // Set the id of OpenTalkIsHosted, in case create new OpenTalk
        if (openTalkIsHostedRepository.findByIdOpenTalk(0L) != null) {
            openTalkIsHostedRepository.findByIdOpenTalk(0L).setIdOpenTalk(savedOpenTalk.getId());
            openTalkIsHostedRepository.save(openTalkIsHostedRepository.findByIdOpenTalk(0L));
        }

        // Update number of joined open talk for user again
        for (Employee employee : mergeEmployeeJoinedOpenTalk) {
            employee.setNumberOfJoinedOpenTalk(employee.getJoinOpenTalkList().size());
            employeeRepository.save(employee);
        }
//        // After saving the open talk, clear the cache
//        redisService.delete("allOpenTalks");
        applicationEventPublisher.publishEvent(new ListenSendEmail(this, modelMapper.map(savedOpenTalk.getHost(), EmployeeDTO.class)));
        return openTalkMapper.entityToDto(savedOpenTalk);
    }

    // delete open talk by id
    @Override
    public void cancelOpenTalk(Long openTalkId) {
        // get open talk by id from repository and throw exception if not found
        Optional<OpenTalk> optionalOpenTalk = openTalkRepository.findById(openTalkId);
        if (optionalOpenTalk.isEmpty()) {
            throw new ItemsNotFoundException("Could not find any open talk with id : " + openTalkId);
        }
//        // After deleting the open talk, clear the cache
//        redisService.delete("allOpenTalks");
        // delete open talk
        openTalkRepository.deleteById(openTalkId);
    }

    @Override
    public Page<OpenTalkDTO> findByEmployeeId(Long employeeId, Integer pageNo, Integer pageSize, LocalDateTime startTime, LocalDateTime endTime) {
        // get employee by id from repository and throw exception if not found
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new ItemsNotFoundException("Could not find any employee with id : " + employeeId);
        }
        // set page number and page size
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        // get open talks by employee id from repository
        Page<OpenTalk> openTalkPage = openTalkRepository.findByEmployeeId(employeeId, startTime, endTime, pageable);
        // convert entity list to dto list and return
        Function<OpenTalk, OpenTalkDTO> function = openTalkMapper::entityToDto;
        return openTalkPage.map(function);
    }

    @Override
    public Page<OpenTalkDTO> findAlreadyFinishedOpenTalks(Integer pageNo, Integer pageSize,
                                                          LocalDateTime startTime, LocalDateTime endTime,
                                                          String branch, String username, LocalDateTime now) {
        // set page number and page size
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, "startTime");
        // get open talks already finished from repository
        Page<OpenTalk> openTalkPage = openTalkRepository.findByAlreadyFinishedOpenTalk(
                branch, LocalDateTime.now(),
                username, startTime,
                endTime, pageable);
        // convert entity list to dto list and return
        Function<OpenTalk, OpenTalkDTO> function = openTalkMapper::entityToDto;
        return openTalkPage.map(function);
    }

    @Override
    public Page<OpenTalkDTO> findIsComingOpenTalks(Integer pageNo, Integer pageSize,
                                                   LocalDateTime startTime, LocalDateTime endTime,
                                                   String branch, String username,
                                                   LocalDateTime estimateDate, LocalDateTime now) {
        // set page number and page size
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "startTime");
        // get open talks is coming from repository
        Page<OpenTalk> openTalkPage = openTalkRepository.findByIsComingOpenTalk(
                branch, username, LocalDateTime.now().plusMonths(1),
                startTime, endTime, LocalDateTime.now(), pageable
        );
        // convert entity list to dto list and return
        Function<OpenTalk, OpenTalkDTO> function = openTalkMapper::entityToDto;
        return openTalkPage.map(function);
    }

//    // Find joined open talks by employee id
//    @Override
//    public List<OpenTalkDTO> getJoinedOpenTalks(Long employeeId) {
//        // Try to get the list from Redis first
//        List<OpenTalk> openTalks = (List<OpenTalk>) redisService.get(String.valueOf(employeeId));
//        if (openTalks == null) {
//            // If the list is not in Redis, get it from the database
//            openTalks = openTalkRepository.findByEmployeeId(employeeId, );
//            // Then store it in Redis for future use
//            redisService.set(String.valueOf(employeeId), openTalks);
//        }
//        return openTalks;
//    }
//
//    // Clear the cache for the given employee
//    @Override
//    public void clearJoinedOpenTalksCache(Long employeeId) {
//        // Clear the cache for the given employee
//        redisService.delete(employeeId);
//    }

    // count open talks has already finished this year by year class
    @Override
    public List<OpenTalkCount> countOpenTalksHasAlreadyFinishedThisYearClass() {
        // get open talks has already finished this year from repository
        return openTalkRepository.countTotalOpenTalksByYearClass();
    }

//    // count open talks has already finished this year by year interface
//    @Override
//    public List<OpenTalkCount> countOpenTalksHasAlreadyFinishedThisYear(Integer year) {
//        // get open talks has already finished this year from repository
//        return openTalkRepository.countTotalOpenTalksByYearInterface();
//    }
}
