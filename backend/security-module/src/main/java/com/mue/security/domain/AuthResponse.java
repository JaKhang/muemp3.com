package com.mue.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer ";
    private boolean isVerified = false;
    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(String token, boolean isVerified) {
        this.isVerified = isVerified;
        this.token = token;
    }
}
