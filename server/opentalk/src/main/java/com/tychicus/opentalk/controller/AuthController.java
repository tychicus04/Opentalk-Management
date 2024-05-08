package com.tychicus.opentalk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tychicus.opentalk.model.MockData;
import com.tychicus.opentalk.request.LoginRequest;
import com.tychicus.opentalk.response.JwtResponse;
import com.tychicus.opentalk.security.jwt.JwtUtils;
import com.tychicus.opentalk.security.user.EmployeeDetailsImpl;
import com.tychicus.opentalk.security.user.OpenTalkEmployeeDetailsService;
import com.tychicus.opentalk.service.impl.AuthService;
import com.tychicus.opentalk.service.IEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class  AuthController {
    private final IEmployeeService employeeService;
    private final AuthenticationManager authManager;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
//    private final UserDetails userDetails;
    private final OpenTalkEmployeeDetailsService openTalkEmployeeDetailsService;

    @Autowired
    public AuthController(IEmployeeService employeeService, AuthenticationManager authManager, AuthService authService, JwtUtils jwtUtils, OpenTalkEmployeeDetailsService openTalkEmployeeDetailsService) {
        this.employeeService = employeeService;
        this.authManager = authManager;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.openTalkEmployeeDetailsService = openTalkEmployeeDetailsService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) throws Exception{
        JwtResponse jwtResponse = authService.login(request);
        return ResponseEntity.ok(
                jwtResponse
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            // Lấy thông tin người dùng từ refreshToken
            String email = jwtUtils.getEmailFromToken(refreshToken);
            EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) openTalkEmployeeDetailsService.loadUserByUsername(email);

            // Tạo accessToken mới từ refreshToken
            String newAccessToken = jwtUtils.generateToken(employeeDetails);
            String newRefreshToken = jwtUtils.generateFreshToken(employeeDetails);

            JwtResponse jwtResponse = new JwtResponse(
                    employeeDetails.getUsername(),
                    newAccessToken,
                    newRefreshToken
            );
            // Trả về accessToken mới
            return ResponseEntity.ok(jwtResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error refreshing token: " + e.getMessage());
        }
    }

    @GetMapping("/google")
    public ResponseEntity<?> loginGoogle(@AuthenticationPrincipal OAuth2User authenticationToken) {
        System.out.println(authenticationToken+"ok");
        try {
            JwtResponse jwtResponse = authService.loginGoogle(authenticationToken);
            System.out.println(jwtResponse+"user");
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error refreshing token: " + e.getMessage());

        }
    }

    @GetMapping()
    public ResponseEntity<?> welcome(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            if (attributes.containsKey("email")) {
                EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl) oAuth2User;
                String token = jwtUtils.generateToken(employeeDetails);
                String refreshToken = jwtUtils.generateToken(employeeDetails);
                JwtResponse jwtResponse = new JwtResponse(
                        employeeDetails.getUsername(),
                        token,
                        refreshToken
                );
                return ResponseEntity.ok(jwtResponse);
            }
        }
        return ResponseEntity.badRequest().body("Error refreshing token: ");
    }

    @GetMapping("/mockdata")
    public MockData getUserData() {
        try {
            // Đọc dữ liệu từ file JSON vào đối tượng UserData
            ObjectMapper objectMapper = new ObjectMapper();
            MockData mockData = objectMapper.readValue(new File("src/main/resources/data.json"), MockData.class);
            return mockData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/say-hello1")
    private ResponseEntity<?> getHello1(){
        String uri = "http://localhost:9192/api/auth/mockdata";
        RestTemplate restTemplate = new RestTemplate();
        MockData data = restTemplate.getForObject(uri,MockData.class);
//        String result = "Gello";
//        return data;
        return new ResponseEntity<>(data.getResult(), HttpStatus.OK);
    }

    @GetMapping("/say-hello")
    @ResponseBody
    private Flux<Object> getHello() {
        String uri = "http://localhost:9192/api/auth/mockdata";
        WebClient client = WebClient.create();

        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Object.class);
    }

    @GetMapping("say-goodbye")
    public Flux<Object> getTweetsNonBlocking() {
//        log.info("Starting NON-BLOCKING Controller!");
        String uri = "https://hrm-api.nccsoft.vn/api/services/app/CheckIn/GetUserForCheckIn";
        Flux<Object> tweetFlux = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Object.class);


        return tweetFlux;
    }
}
