//package com.tychicus.opentalk.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
//import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
//import com.tychicus.opentalk.model.CompanyBranch;
//import com.tychicus.opentalk.model.Employee;
//import com.tychicus.opentalk.model.OpenTalk;
//import com.tychicus.opentalk.model.Role;
//import com.tychicus.opentalk.repository.OpenTalkRepository;
//import com.tychicus.opentalk.service.impl.AuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.tychicus.opentalk.dto.employee.EmployeeDTO;
//import com.tychicus.opentalk.service.impl.EmployeeService;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//public class EmployeeControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AuthService authService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Mock
//    private OpenTalkRepository openTalkRepository;
//
//    @MockBean
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
//        companyBranch.setName("HN1");
//
//        // Initialize Role
//        Role role = new Role();
//        role.setId(1);
//        role.setName("ROLE_ADMIN");
//
//        // Initialize OpenTalk
//        openTalk = new OpenTalk();
//        openTalk.setId(1L);
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
//        employee.setRoles(Collections.singleton(role));
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
//        employeeDTO.setRoles(Collections.singleton("ROLE_ADMIN"));
//        employeeDTO.setEmail("john.doe@example.com");
//        employeeDTO.setCompanyBranch("HN1");
//
//        // Initialize OpentalkDTO
//        OpenTalkDTO openTalkDTO = new OpenTalkDTO();
//        openTalkDTO.setId(1L);
//        openTalkDTO.setTopic("OpenTalk1");
//        openTalkDTO.setStartTime(LocalDateTime.parse("2024-04-25T11:13"));
//        openTalkDTO.setEndTime(LocalDateTime.parse("2024-04-25T11:15"));
//        openTalkDTO.setCompanyBranchName("HN1");
//        openTalkDTO.setLinkMeeting("https://meet.google.com/abc-xyz");
//    }
//
//    // Add test methods here
//
//    @Test
//    void testGetEmployeeById() throws Exception {
//        // Arrange
//        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDTO);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employee/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.username", is("john.doe")))
//                .andExpect(jsonPath("$.fullName", is("John Doe")))
//                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
//                .andExpect(jsonPath("$.companyBranch", is("HN1")));
//
//        verify(employeeService).getEmployeeById(anyLong());
//    }
//
//    @Test
//    void testFindRandomEmployeeForHost() throws Exception {
//        // Arrange
//        when(employeeService.findRandomEmployeeForHost()).thenReturn(employeeDTO);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/employee/admin/random-host"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.username", is("john.doe")))
//                .andExpect(jsonPath("$.fullName", is("John Doe")))
//                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
//                .andExpect(jsonPath("$.companyBranch", is("HN1")));
//
//        verify(employeeService).findRandomEmployeeForHost();
//    }
//}
