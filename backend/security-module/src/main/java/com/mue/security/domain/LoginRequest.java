package com.mue.security.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email must not empty")
    private String email;
    @NotEmpty
    private String password;
}
