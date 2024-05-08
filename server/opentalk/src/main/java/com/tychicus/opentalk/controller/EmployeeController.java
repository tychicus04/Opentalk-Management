package com.tychicus.opentalk.controller;
import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.employee.EmployeeDTOModify;
//import com.tychicus.opentalk.response.RoleResponse;
import com.tychicus.opentalk.service.IEmployeeService;
import com.tychicus.opentalk.service.impl.OpenTalkService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/employee")
public class EmployeeController {
    private final IEmployeeService employeeService;

    // Get all employees
    @GetMapping("/all-employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }

    // Add new employee
    @PostMapping("/admin/add")
    public ResponseEntity<EmployeeDTO> addNewEmployee(@RequestBody EmployeeDTOModify employeeDTOModify) {
        // Set id to 0 to create new employee instead of update existing employee
//        employeeDTOModify.setId(0L);
        EmployeeDTO employeeDTO = employeeService.save(employeeDTOModify);
        return ResponseEntity.ok(employeeDTO);
    }

    // Update employee
    @PutMapping("/update")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTOModify employeeDTOModify){
        EmployeeDTO employeeDTO = employeeService.save(employeeDTOModify);
        return new ResponseEntity<EmployeeDTO>(employeeDTO, HttpStatus.OK);
    }

    // Delete employee by id
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete all employees
    @DeleteMapping("/admin/delete/all")
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find employee by some filter
    @GetMapping("/filter")
    public ResponseEntity<Page<EmployeeDTO>> findEmployeeBySomeFilter(
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize
    ) {
        Page<EmployeeDTO> employeeDTOPage = employeeService.findEmployeeBySomeFilter(enabled, branch, username, pageNo, pageSize);
        return new ResponseEntity<>(employeeDTOPage, HttpStatus.OK);
    }

    // Find employee joined open talk sort by ascending the number of joined open talk
    @GetMapping("/sortByOT")
    public ResponseEntity<Page<EmployeeDTO>> sortByNumberOfOpenTalk(
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String branch,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Page<EmployeeDTO> employeeDTOPage = employeeService.findEmployeeBySortByNoOfOpenTalk(enabled, branch, username, pageNo, pageSize);
        return ResponseEntity.ok().body(employeeDTOPage);
    }

    @Schema(description = "Register host open talk")
    @PutMapping("/register-host")
    public ResponseEntity<String> registerHostOpenTalk(
            @RequestParam(required = true) Long employeeId,
            @RequestParam(required = true) Long openTalkId
    ) {
        employeeService.registerHost(employeeId, openTalkId);
        return ResponseEntity.ok().body("Register host user id: " + employeeId + " for Open Talk id: " + openTalkId);
    }

    @Schema(description = "Register join open talk")
    @PutMapping("/register-join")
    public ResponseEntity<String> registerJoinOpenTalk(
            @RequestParam(required = true) Long employeeId,
            @RequestParam(required = true) Long openTalkId
    ) {
        employeeService.registerJoinedOpenTalk(employeeId, openTalkId);
        return ResponseEntity.ok().body("Register user id: " + employeeId + " join Open Talk id: " + openTalkId);
    }

    // Find employee has not hosted open talk in this year, or has not hosted any open talk
    @GetMapping("/admin/has-not-hosted")
    public ResponseEntity<Page<EmployeeDTO>> findEmployeeHaveNotHostedOpenTalk(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "3") Integer pageSize
    ) {
        Page<EmployeeDTO> employeeDTOPage = employeeService.findEmployeeHasNotHostedOpenTalk(pageNo, pageSize);
        return new ResponseEntity<>(employeeDTOPage, HttpStatus.OK);
    }

    // Find random employee to host open talk
    @GetMapping("/admin/random-host")
    public ResponseEntity<EmployeeDTO> findRandomEmployeeToHostOpenTalk() {
        EmployeeDTO employeeDTO = employeeService.findRandomEmployeeForHost();
        return ResponseEntity.ok().body(employeeDTO);
    }

    // Get all employee projections
    @GetMapping("/projections")
    public ResponseEntity<?> getEmployeeProjections() {
        return ResponseEntity.ok().body(employeeService.getEmployeeProjections());
    }

    // get employee by email
    @GetMapping("/admin/email/{email}")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(@PathVariable String email) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok().body(employeeDTO);
    }

    @GetMapping("/account")
    private ResponseEntity<?> SyncData() throws Exception {
        Boolean bool = employeeService.syncDataFromServer();
        if (bool == null) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (bool == false) {
            return new ResponseEntity<>("No new data", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sync data successfully", HttpStatus.OK);
        }
    }

    @GetMapping("/admin/accounts")
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok().body(employeeService.getAllAccounts());
    }

    // Get employee by username
    @GetMapping("/username/{username}")
    public ResponseEntity<EmployeeDTO> getEmployeeByUsername(@PathVariable String username) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByUsername(username);
        return ResponseEntity.ok().body(employeeDTO);
    }
}
