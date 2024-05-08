package com.tychicus.opentalk.service.impl;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.employee.EmployeeDTOModify;
import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import com.tychicus.opentalk.event.ListenSendEmail;
import com.tychicus.opentalk.exception.BadInputException;
import com.tychicus.opentalk.exception.ResourceNotFoundException;
import com.tychicus.opentalk.mapper.EmployeeMapper;
import com.tychicus.opentalk.mapper.ModifyEmployeeMapper;
import com.tychicus.opentalk.model.*;
import com.tychicus.opentalk.projection.EmployeeProjection;
import com.tychicus.opentalk.repository.*;
import com.tychicus.opentalk.service.IEmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
@CacheConfig(cacheNames = "employees")
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyBranchRepository companyBranchRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ModifyEmployeeMapper modifyEmployeeMapper;
    @Autowired
    private OpenTalkRepository openTalkRepository;
    @Autowired
    private OpenTalkIsHostedRepository openTalkIsHostedRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;

    // get all employees
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMapper.entityListToDtoList(employeeRepository.findAll());
    }

    // get employee by id
    @Override
    @Cacheable(key = "#id")
    public EmployeeDTO getEmployeeById(Long id) {
        // get employee by id from repository and throw exception if not found
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employeeMapper.entityToDto(employeeOptional.get());
    }

    // create/update employee
    @Override
//    @CachePut(key = "#employeeDTOModify.id")
    public EmployeeDTO save(EmployeeDTOModify employeeDTOModify) {
        // get employee from DTO
        Employee employee = modifyEmployeeMapper.getEmployeeForModify(employeeDTOModify);
        // email each employee must be unique
        Optional<Employee> employeeByEmail = employeeRepository.findByEmail(employee.getEmail());
        if (employeeByEmail.isPresent() && !employeeByEmail.get().getId().equals(employee.getId())) {
            throw new BadInputException("Email is already taken");
        } else {
            // encode and set password
            String passwordEncoded = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(passwordEncoded);
            // set role
            Set<Role> roles = new HashSet<>();
            if (employeeDTOModify.getRolesId() != null && !employeeDTOModify.getRolesId().isEmpty()) {
                for (Integer roleId : employeeDTOModify.getRolesId()) {
                    Optional<Role> roleOptional = roleRepository.findById(roleId);
                    if (roleOptional.isEmpty()) {
                        throw new BadInputException("Can not find any role with id : " + roleId);
                    }
                    roles.add(roleOptional.get());
                }
            } else {
                Optional<Role> roleOptionalEmployee = roleRepository.findById(2);
                roleOptionalEmployee.ifPresent(roles::add);
            }
            employee.setRoles(roles);
            // set enabled if null or not null (default is true)
            if (employee.isEnabled()) {
                employee.setEnabled(true);
            } else {
                employee.setEnabled(employee.isEnabled());
            }
            // set open talk set
            Set<OpenTalk> openTalkSet = new HashSet<>();
            for (Long openTalkId : employeeDTOModify.getJoinOpenTalkListId()) {
                Optional<OpenTalk> openTalkOptional = openTalkRepository.findById(openTalkId);
                openTalkOptional.ifPresent(openTalkSet::add);
            }
            // set number of joined open talk for employee and set open talk list
            employee.setJoinOpenTalkList(openTalkSet);
            employee.setNumberOfJoinedOpenTalk(openTalkSet.size());
            // set company branch
            Optional<CompanyBranch> companyBranchOptional
                    = companyBranchRepository.findByName(employeeDTOModify.getCompanyBranchName());
            if (companyBranchOptional.isEmpty()) {
                throw new BadInputException("Can not find any company branch with id : "
                        + employeeDTOModify.getCompanyBranchName());
            }
            employee.setCompanyBranch(companyBranchOptional.get());
            // save employee
            Employee savedEmployee = employeeRepository.save(employee);
            // return employee DTO
            return employeeMapper.entityToDto(savedEmployee);
        }
    }

    @CacheEvict(allEntries = true)
    public boolean clearCache() {

        return true;
    }

    // delete employee by id
    @Override
    public void deleteEmployee(Long id) {
        // get employee by id from repository and throw exception if not found
        Optional<Employee> theEmployee = employeeRepository.findById(id);
        if (theEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee not found");
        }
    }

    // delete all employees
    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    // get employee by name
    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        // get employee by email from repository and throw exception if not found
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employeeMapper.entityToDto(employeeOptional.get());
    }

    // find employee by some filter
    @Override
    @CachePut(key = "#username")
    public Page<EmployeeDTO> findEmployeeBySomeFilter(Boolean enabled, String branch, String username, Integer pageNo, Integer pageSize) {
        // set pageable for pagination
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        // get employee by some filter from repository
        Page<Employee> employeePage = employeeRepository.findEmployeeBySomeFilter(enabled, branch, username, pageable);
        // map employee to employee DTO and return
        Function<Employee, EmployeeDTO> function = employeeMapper::entityToDto;
        return employeePage.map(function);
    }



    // find employee joined open talk sort by ascending the number of joined open talk
    @Override
    public Page<EmployeeDTO> findEmployeeBySortByNoOfOpenTalk(Boolean enabled, String branch, String username, Integer pageNo, Integer pageSize) {
        // sort by number of joined open talk
        Sort sortByNumberOfJoinedOpenTalk = Sort.by(Sort.Direction.ASC, "numberOfJoinedOpenTalk");
        // set pageable for pagination
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortByNumberOfJoinedOpenTalk);
        // get employee by sort by number of joined open talk from repository
        Page<Employee> employeePage = employeeRepository.findEmployeeBySortByNoOfOpenTalk(enabled, branch, username, pageable);
        // map employee to employee DTO and return
        Function<Employee, EmployeeDTO> function = employeeMapper::entityToDto;
        return employeePage.map(function);
    }

    // register employee as host for open talk
    @Override
    public void registerHost(Long idHost, Long idOpenTalk) {
        // get employee and open talk by id from repository and throw exception if not found
        Optional<Employee> employeeOptional = employeeRepository.findById(idHost);
        if (employeeOptional.isEmpty()) {
            throw new BadInputException("Could not find any user with id: " + idHost);
        }
        Optional<OpenTalk> openTalkOptional = openTalkRepository.findById(idOpenTalk);
        if (openTalkOptional.isEmpty()) {
            throw new BadInputException("Could not find any open talk with id: " + idOpenTalk);
        }
        Employee employee = employeeOptional.get();
        OpenTalk openTalk = openTalkOptional.get();
        // register host open talk for employee
        employee.registerHost(openTalk);
        // Set OpenTalkIsHosted for user
        // Check OpenTalkIsHosted is existed in DB or not
        OpenTalkIsHosted openTalkIsHosted = openTalkIsHostedRepository.findByIdOpenTalk(openTalk.getId());
        // Exist, set employee again
        if (openTalkIsHosted != null) {
            openTalkIsHosted.setEmployee(employee);
            openTalkIsHostedRepository.save(openTalkIsHosted);
        } else {
            OpenTalkIsHosted newOpenTalkIsHosted = new OpenTalkIsHosted();
            newOpenTalkIsHosted.setId(0L);
            newOpenTalkIsHosted.setEmployee(employee);
            openTalkIsHostedRepository.save(newOpenTalkIsHosted);
        }
        // save employee
        employeeRepository.save(employee);
    }

    // register employee as joined open talk
    @Override
    public void registerJoinedOpenTalk(Long idHost, Long idOpenTalk) {
        // get employee and open talk by id from repository and throw exception if not found
        Optional<Employee> employeeOptional = employeeRepository.findById(idHost);
        if (employeeOptional.isEmpty()) {
            throw new BadInputException("Could not find any user with id: " + idHost);
        }
        Optional<OpenTalk> openTalkOptional = openTalkRepository.findById(idOpenTalk);
        if (openTalkOptional.isEmpty()) {
            throw new BadInputException("Could not find any open talk with id: " + idOpenTalk);
        }
        Employee employee = employeeOptional.get();
        OpenTalk openTalk = openTalkOptional.get();
        // register joined open talk for employee
        employee.registerJoinedOpenTalk(openTalk);
        // save employee
        employeeRepository.save(employee);
    }

    // find employee have not hosted open talk in this year, or have not hosted any open talk
    @Override
    public Page<EmployeeDTO> findEmployeeHasNotHostedOpenTalk(Integer pageNo, Integer pageSize) {
        // get employee have not hosted open talk
        List<Employee> employees = getEmployeeHasNotHostedOpenTalk();
        // set pageable for pagination
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        // create page of employee from list of employee and pageable
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());
        // map employee to employee DTO and return
        Function<Employee, EmployeeDTO> function = employeeMapper::entityToDto;
        return employeePage.map(function);
    }

    // get employee by company branch name to send email
    @Override
    public Collection<EmployeeProjection> getEmployeeProjections() {
        return employeeRepository.findBy(EmployeeProjection.class);
    }

    // find random employee for host open talk
    @Override
    public EmployeeDTO findRandomEmployeeForHost() {
        // get employee has not hosted open talk
        List<Employee> employeeHasNotHostedOpenTalk = getEmployeeHasNotHostedOpenTalk();
        // map employee to employee DTO
        Function<Employee, EmployeeDTO> function = employeeMapper::entityToDto;
        // get random employee from list of employee DTO
        List<EmployeeDTO> employeeDTOList = employeeHasNotHostedOpenTalk.stream().map(function).collect(Collectors.toList());
        Random rand = new Random();
        // return random employee DTO

        return employeeDTOList.get(rand.nextInt(employeeDTOList.size()));
    }

    // get employee has not hosted open talk
    private List<Employee> getEmployeeHasNotHostedOpenTalk() {
        // get all employees
        List<Employee> employees = employeeRepository.findAll();
        // employee has not hosted any open talk
        List<Employee> employeeHasNotHostedOpenTalk = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getOpenTalkIsHosted().size() == 0) {
                employeeHasNotHostedOpenTalk.add(employee);
            }
        }
        // employee has not hosted for this year
        List<Employee> employeesHasNotHostedForThisYear = employeeRepository.findEmployeesByHasNotHostedThisYear(2024);
        Set<Employee> employeeSet = new HashSet<>(employeeHasNotHostedOpenTalk);
        employeeSet.addAll(employeesHasNotHostedForThisYear);
        // merge and sort by number of joined open talk for employee and return
        return employeeSet.stream().sorted(Comparator.comparing(Employee::getNumberOfJoinedOpenTalk)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean syncDataFromServer() throws Exception {
        String uri = "http://localhost:9192/api/auth/mockdata";
        RestTemplate restTemplate = new RestTemplate();
        MockData data = restTemplate.getForObject(uri,MockData.class);
        if(data != null){
            List<Account> accountsList = data.getResult();
            // Fetch all existing emails from the Employee table
            List<String> existingEmails = employeeRepository.findAllEmails();
            // Store the emails in a HashSet for constant time access
            HashSet<String> emailsSet = new HashSet<>(existingEmails);
            for (Account account : accountsList) {
                String newEmail = 2 + account.getEmail();
                if (!emailsSet.contains(account.getEmail())) {
                    account.setEmail(newEmail);
                    accountRepository.save(account);
                }
            }
            System.out.println("LƯU THÀNH CÔNG");
            return true;
        }
        return false;
    }

    // get all accounts
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // get employee by username
    @Override
    public EmployeeDTO getEmployeeByUsername(String username) {
        // get employee by username from repository and throw exception if not found
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(username);
        if (employeeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employeeMapper.entityToDto(employeeOptional.get());
    }

}
