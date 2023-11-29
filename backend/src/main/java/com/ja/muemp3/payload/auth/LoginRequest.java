package com.ja.muemp3.payload.auth;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

    private String email;
    private String password;
}
