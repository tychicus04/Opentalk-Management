package com.tychicus.opentalk.service.impl;

import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.Role;
import com.tychicus.opentalk.repository.EmployeeRepository;
import com.tychicus.opentalk.request.LoginRequest;
import com.tychicus.opentalk.response.JwtResponse;
import com.tychicus.opentalk.security.jwt.JwtUtils;
import com.tychicus.opentalk.security.user.EmployeeDetailsImpl;
import com.tychicus.opentalk.security.user.OpenTalkEmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmployeeRepository employeeRepository;
    private final OpenTalkEmployeeDetailsService openTalkEmployeeDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder  passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) authentication.getPrincipal();
        String token = jwtUtils.generateToken(employeeDetails);
        String refreshToken = jwtUtils.generateFreshToken(employeeDetails);

        return new JwtResponse(
                employeeDetails.getUsername(),
                token,
                refreshToken
        );
    }

    public JwtResponse loginGoogle(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String googleId = oAuth2User.getAttribute("sub");
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);

        if (employeeOptional.isEmpty()) {
            throw new RuntimeException("Account does not exist"); // or return a specific response
        }

        Employee employee = employeeOptional.get();

        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) openTalkEmployeeDetailsService.loadUserByUsername(employee.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(employeeDetails, null, employeeDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(employeeDetails);
        String refreshToken = jwtUtils.generateFreshToken(employeeDetails);

        return new JwtResponse(
                employeeDetails.getUsername(),
                token,
                refreshToken
        );
    }

    private Authentication authenticate(String username, String password) {
        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) openTalkEmployeeDetailsService.loadUserByUsername(username);

        if(employeeDetails == null){

            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password,employeeDetails.getPassword())){

            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(employeeDetails,null,employeeDetails.getAuthorities());
    }
}
