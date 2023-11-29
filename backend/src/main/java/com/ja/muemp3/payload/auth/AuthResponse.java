package com.ja.muemp3.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer ";
    public AuthResponse(String token) {
        this.token = token;
    }
}
