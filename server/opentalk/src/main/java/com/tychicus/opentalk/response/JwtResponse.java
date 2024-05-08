package com.tychicus.opentalk.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponse {
    private String email;
    private String token;
    private String refreshToken;
    private String type = "Bearer";

    public JwtResponse(String email, String token, String refreshToken) {
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
