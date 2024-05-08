package com.tychicus.opentalk.service.impl;

import com.tychicus.opentalk.dto.branch.CompanyBranchDTO;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.exception.ResourceNotFoundException;
import com.tychicus.opentalk.mapper.CompanyBranchMapper;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.OpenTalk;
import com.tychicus.opentalk.repository.CompanyBranchRepository;
import com.tychicus.opentalk.repository.OpenTalkRepository;
import com.tychicus.opentalk.service.ICompanyBranchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanyBranchService implements ICompanyBranchService {
    @Autowired
    private CompanyBranchRepository companyBranchRepository;
    @Autowired
    private CompanyBranchMapper companyBranchMapper;
    @Autowired
    private OpenTalkRepository openTalkRepository;
    @Autowired
    private ModelMapper mapper;

    // get all company branches
    @Override
    public List<CompanyBranchDTO> getAllCompanyBranch() {
        return companyBranchMapper.entityListToDtoList(companyBranchRepository.findAll());
    }
    // get company branch by id
    @Override
    public CompanyBranchDTO getCompanyBranchById(Long id) {
        // get company branch by id from repository and throw exception if not found
        Optional<CompanyBranch> theCompanyBranch = companyBranchRepository.findById(id);
        if (theCompanyBranch.isPresent()) {
            CompanyBranchDTO companyBranchDTO = mapper.map(theCompanyBranch.get(), CompanyBranchDTO.class);
            return companyBranchDTO;
        } else {
            throw new ResourceNotFoundException("Company Branch not found with id: " + id);
        }
    }
    // create/update company branch
    @Override
    public CompanyBranchDTO save(CompanyBranchDTO companyBranchDTO) {
        // convert DTO to entity
        CompanyBranch companyBranch = companyBranchMapper.dtoToEntity(companyBranchDTO);
        // set open talks for the company branch if available in the DTO else set to null
        Set<OpenTalk> openTalks = new HashSet<>();
        if (companyBranchDTO.getOpenTalks() != null) {
            for (EntityOpenTalkDTO openTalkDTO : companyBranchDTO.getOpenTalks()) {
                Optional<OpenTalk> optionalOpenTalk = openTalkRepository.findById(openTalkDTO.getId());
                optionalOpenTalk.ifPresent(openTalks::add);
            }
            companyBranch.setOpenTalks(openTalks);
        } else {
            companyBranch.setOpenTalks(null);
        }
        // save the company branch
        CompanyBranch savedCompanyBranch = companyBranchRepository.save(companyBranch);
        // return the saved company branch
        return companyBranchMapper.entityToDto(savedCompanyBranch);
    }
    // delete company branch by id
    @Override
    public void deleteCompanyBranch(Long id) {
        // get company branch by id from repository and throw exception if not found
        Optional<CompanyBranch> theCompanyBranch = companyBranchRepository.findById(id);
        if (theCompanyBranch.isPresent()) {
            companyBranchRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Company Branch not found with id: " + id);
        }
    }
}
