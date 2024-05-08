package com.tychicus.opentalk.security.user;

import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OpenTalkEmployeeDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isEmpty()) {
            //log.error("User not found in database");
            throw  new UsernameNotFoundException("User not found in database");
        }
        // log.info("User found in database: {}",username);

        return new EmployeeDetailsImpl(employee.get());
    }



}
