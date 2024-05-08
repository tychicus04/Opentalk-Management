//package com.tychicus.opentalk.repository;
//
//import com.tychicus.opentalk.OpentalkApplication;
//import com.tychicus.opentalk.model.CompanyBranch;
//import com.tychicus.opentalk.model.Employee;
//import org.assertj.core.api.Assertions;
//import org.assertj.core.api.OptionalAssert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//
////@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
////        replace = AutoConfigureTestDatabase.Replace.ANY)
//@SpringBootTest(classes = {OpentalkApplication.class})
//public class EmployeeRepositoryTest {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private CompanyBranchRepository companyBranchRepository;
//
//    @BeforeEach
//    public void init() {
//
//        CompanyBranch companyBranch = CompanyBranch.builder()
//                .name("HN4")
//                .build();
//        CompanyBranch companyBranch1 = companyBranchRepository.save(companyBranch);
//
////
//        Employee employee1 = Employee.builder()
//                .email("tai217@gmail.com")
//                .companyBranch(companyBranch1)
//                .enabled(true)
//                .fullName("Nguyen Minh Tai")
//                .username("tai217")
//                .password("$2a$10$A6A1vx72OxAql1yBcLxFieI.JB12Xmmt50kMF7QZR2OL1c7i47iWm")
//                .build();
//        employeeRepository.save(employee1);
////
//        Employee employee2 = Employee.builder()
//                .email("trung02@gmail.com")
//                .companyBranch(companyBranch1)
//                .enabled(true)
//                .fullName("Vu Van Trung")
//                .username("trung02")
//                .password("$2a$10$A6A1vx72OxAql1yBcLxFieI.JB12Xmmt50kMF7QZR2OL1c7i47iWm")
//                .build();
//        employeeRepository.save(employee2);
//////
//        Employee employee3 = Employee.builder()
//                .email("taitai@gmail.com")
//                .companyBranch(companyBranch1)
//                .enabled(true)
//                .fullName("Nguyen Tai")
//                .username("tainguyen01")
//                .password("$2a$10$oABE.Be/tIR8D3iR54ZSA.dDk2i2z3XALOuKgZUJRRzu9u9/GfbVS")
//                .build();
//
//        employeeRepository.save(employee3);
//
//    }
//
////
//    @Test
//    public void testFindEmployeeByCompanyBranch() {
//        List<Employee> result = employeeRepository.findEmployeeByEnabledAndCompanyBranch("HN4");
//        Assertions.assertThat(result.size()).isGreaterThan(0);
//    }
//////
//    @Test
//    public void testFindEmployeeBySomeFilter() {
//        Pageable pageable = PageRequest.of(0, 3);
//        Page<Employee> result = employeeRepository.findEmployeeBySomeFilter(true, "HN", "", pageable);
//        Assertions.assertThat(result.getContent()).isNotNull();
//        Assertions.assertThat(result.getContent().size()).isEqualTo(3);
//    }
//////
//    @Test
//    public void testFindEmployeeBySortByNoOfOpenTalk() {
//        Pageable pageable = PageRequest.of(0, 3);
//        Page<Employee> result = employeeRepository.findEmployeeBySortByNoOfOpenTalk(true, "HN", "", pageable);
//        Assertions.assertThat(result.getContent()).isNotNull();
//        Assertions.assertThat(result.getContent().size()).isEqualTo(3);
//    }
////
//    @Test
//    public void testFindEmployeeForSendEmail() {
//        List<Employee> result = employeeRepository.findEmployeeForSendEmail("HN4");
//        Assertions.assertThat(result.size()).isGreaterThan(0);
//    }
//
//}
