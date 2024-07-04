package com.mue.security.services;

import com.mue.security.domain.UserPrincipal;
import com.mue.security.domain.LoginRequest;
import com.mue.security.domain.RegisterRequest;
import com.mue.security.domain.AuthResponse;
import com.mue.security.domain.PrincipleResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthService {
    UserPrincipal register(RegisterRequest request);

    AuthResponse authenticate(LoginRequest loginRequest);

    void sendConfirmationEmail(String email);

    UserDetails confirmToken(String token);

    PrincipleResponse getUserInfoById(UUID id);

    PrincipleResponse getPrinciple(UUID id);
}
