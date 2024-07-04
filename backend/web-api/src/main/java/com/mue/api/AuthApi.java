package com.mue.api;

import com.mue.core.domain.RestBody;
import com.mue.core.exception.UnauthorizedException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.security.annotaton.IsAuthenticated;
import com.mue.security.domain.*;
import com.mue.security.services.JwtProvider;
import com.mue.security.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthApi {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt);
        RestBody<AuthResponse> restBody = new RestBody<>("Login success !",  authResponse);
        return ResponseEntity.ok(restBody);
    }

    @PostMapping("/register")
    public ResponseEntity<RestBody<?>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserPrincipal userDetails = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestBody<>("Register success", userDetails.getId()));
    }


}
