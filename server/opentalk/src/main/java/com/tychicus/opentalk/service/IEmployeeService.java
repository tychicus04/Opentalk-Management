package com.tychicus.opentalk.service;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.employee.EmployeeDTOModify;
import com.tychicus.opentalk.model.Account;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.projection.EmployeeProjection;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface IEmployeeService {
    // get employee by id
    EmployeeDTO getEmployeeById(Long Id);

    // get all employees
    List<EmployeeDTO> getAllEmployees();

    // create/update employee
    EmployeeDTO save(EmployeeDTOModify employee);

    // delete employee by id
    void deleteEmployee(Long id);

    // delete all employees
    void deleteAllEmployees();

    boolean clearCache();

    // get employee by name
    EmployeeDTO getEmployeeByEmail(String email);

    // Find employee based on some filter (enabled, branch, username)
    Page<EmployeeDTO> findEmployeeBySomeFilter(Boolean enabled, String branch, String username, Integer pageNo, Integer pageSize);

    // Find employee joined open talk sort by ascending the number of joined open talk
    Page<EmployeeDTO> findEmployeeBySortByNoOfOpenTalk(Boolean enabled, String branch, String username, Integer pageNo, Integer pageSize);

    // register host open talk for employee
    void registerHost(Long idHost, Long idOpenTalk);

    // register joined open talk for employee
    void registerJoinedOpenTalk(Long idHost, Long idOpenTalk);

    // find employee have not hosted open talk in this year, or have not hosted any open talk
    Page<EmployeeDTO> findEmployeeHasNotHostedOpenTalk(Integer pageNo, Integer pageSize);

    // find random employee for host open talk
    EmployeeDTO findRandomEmployeeForHost();

    Collection<EmployeeProjection> getEmployeeProjections();

    boolean syncDataFromServer() throws Exception;

    List<Account> getAllAccounts();

    EmployeeDTO getEmployeeByUsername(String username);
}
