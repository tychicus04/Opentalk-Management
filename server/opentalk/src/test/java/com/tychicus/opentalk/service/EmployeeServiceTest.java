//package com.tychicus.opentalk.service;
//
//import com.tychicus.opentalk.dto.employee.EmployeeDTO;
//import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
//import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
//import com.tychicus.opentalk.mapper.EmployeeMapper;
//import com.tychicus.opentalk.mapper.ModifyEmployeeMapper;
//import com.tychicus.opentalk.model.CompanyBranch;
//import com.tychicus.opentalk.model.Employee;
//import com.tychicus.opentalk.model.OpenTalk;
//import com.tychicus.opentalk.repository.*;
//import com.tychicus.opentalk.service.impl.EmployeeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class EmployeeServiceTest {
//    @Mock
//    private EmployeeRepository employeeRepository;
//    @Mock
//    private EmployeeMapper employeeMapper;
//    @Mock
//    private OpenTalkRepository openTalkRepository;
//
//    @InjectMocks
//    private EmployeeService employeeService;
//
//    private Employee employee;
//    private EmployeeDTO employeeDTO;
//    private CompanyBranch companyBranch;
//    private OpenTalk openTalk;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        initEntitiesAndDTOs();
//    }
//
//    public void initEntitiesAndDTOs() {
//        // Initialize CompanyBranch
//        companyBranch = new CompanyBranch();
//        companyBranch.setId(1L);
//        companyBranch.setName("Branch1");
//
//        // Initialize OpenTalk
//        openTalk = new OpenTalk();
//        openTalk.setId(2L);
//        openTalk.setTopic("OpenTalk1");
//        openTalk.setStartTime(LocalDateTime.parse("2024-04-25T11:13"));
//        openTalk.setEndTime(LocalDateTime.parse("2024-04-25T11:15"));
//        openTalk.setCompanyBranch(companyBranch);
//        openTalk.setMeetingLink("https://meet.google.com/abc-xyz");
//        openTalk.setEnabled(true);
//        openTalk.setSlide(null);
//        openTalk.setHost(employee);
//        openTalk.setParticipants(Collections.singleton(employee));
//
//        // Initialize OpenTalkIsHosted
//        OpenTalkIsHosted openTalkIsHosted = new OpenTalkIsHosted();
//        openTalkIsHosted.setId(openTalk.getId());
//        openTalkIsHosted.setTopic(openTalk.getTopic());
//        openTalkIsHosted.setStartTime(openTalk.getStartTime());
//        openTalkIsHosted.setEndTime(openTalk.getEndTime());
//
//
//        // Initialize Employee
//        employee = new Employee();
//        employee.setId(1L);
//        employee.setUsername("john.doe");
//        employee.setFullName("John Doe");
//        employee.setEnabled(true);
//        employee.setEmail("john.doe@example.com");
//        employee.setCompanyBranch(companyBranch);
//        employee.setOpenTalkIsHosted(Collections.singleton(openTalkIsHosted));
//
//        // Initialize EmployeeDTO
//        employeeDTO = new EmployeeDTO();
//        employeeDTO.setId(1L);
//        employeeDTO.setUsername("john.doe");
//        employeeDTO.setFullName("John Doe");
//        employeeDTO.setEmail("john.doe@example.com");
//        employeeDTO.setCompanyBranch("HN1");
//
//        // Initialize OpentalkDTO
//        OpenTalkDTO openTalkDTO = new OpenTalkDTO();
//        openTalkDTO.setId(2L);
//        openTalkDTO.setTopic("OpenTalk1");
//        openTalkDTO.setStartTime(LocalDateTime.parse("2024-04-25T11:13"));
//        openTalkDTO.setEndTime(LocalDateTime.parse("2024-04-25T11:15"));
//        openTalkDTO.setCompanyBranchName("HN1");
//        openTalkDTO.setLinkMeeting("https://meet.google.com/abc-xyz");
//    }
//
//    // Test getEmployeeById
//    @Test
//    void testGetEmployeeById() {
//        // Arrange
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
//        when(employeeMapper.entityToDto(any(Employee.class))).thenReturn(employeeDTO);
//
//        // Act
//        EmployeeDTO result = employeeService.getEmployeeById(1L);
//
//        // Assert
//        assertEquals(employeeDTO, result);
//        verify(employeeRepository).findById(anyLong());
//        verify(employeeMapper).entityToDto(any(Employee.class));
//    }
//
////    // Test register host open talk for employee
////    @Test
////    void testRegisterHost() {
////        // Arrange
////        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
////        when(openTalkRepository.findById(anyLong())).thenReturn(Optional.of(openTalk));
////
////        // Act
////        employeeService.registerHost(1L, 2L);
////
////        // Assert
////        verify(employeeRepository).findById(anyLong());
////        verify(openTalkRepository).findById(anyLong());
////        verify(employeeRepository).save(any(Employee.class));
////    }
//
//    // Test register joined open talk for employee
//    @Test
//    void testRegisterJoinedOpenTalk() {
//        // Arrange
//        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
//        when(openTalkRepository.findById(anyLong())).thenReturn(Optional.of(openTalk));
//
//        // Act
//        employeeService.registerJoinedOpenTalk(1L, 1L);
//
//        // Assert
//        verify(employeeRepository).findById(anyLong());
//        verify(openTalkRepository).findById(anyLong());
//        verify(employeeRepository).save(any(Employee.class));
//    }
//
//    // Test find random employee for host open talk
//    @Test
//    void testFindRandomEmployeeForHost() {
//        // Arrange
//
//        Employee employee2 = new Employee();
//        employee2.setId(2L);
//        employee2.setUsername("jane.doe");
//        employee2.setFullName("Jane Doe");
//        employee2.setEnabled(true);
//        employee2.setEmail("jane.doe@example.com");
//        employee2.setCompanyBranch(companyBranch);
//        employee2.setOpenTalkIsHosted(Collections.emptySet());
//
//        List<Employee> employees = Arrays.asList(employee, employee2);
//
//        when(employeeRepository.findAll()).thenReturn(employees);
//        when(employeeRepository.findEmployeesByHasNotHostedThisYear(anyInt())).thenReturn(employees);
//        when(employeeMapper.entityToDto(any(Employee.class))).thenReturn(employeeDTO);
//
//        // Act
//        EmployeeDTO result = employeeService.findRandomEmployeeForHost();
//
//        // Assert
//        assertNotNull(result);
//        verify(employeeRepository).findAll();
//        verify(employeeRepository).findEmployeesByHasNotHostedThisYear(anyInt());
//        verify(employeeMapper, times(2)).entityToDto(any(Employee.class)); // called twice because there are two employees
//    }
//}
