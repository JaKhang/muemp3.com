package com.mue.security.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email must not empty")
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty(message = "Full name must not be null")
    private String fullName;
}
