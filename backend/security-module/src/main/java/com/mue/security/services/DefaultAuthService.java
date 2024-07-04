package com.mue.security.services;

import com.mue.core.exception.EmailAlreadyExistsException;
import com.mue.security.domain.UserPrincipal;
import com.mue.entities.User;
import com.mue.enums.AuthProvider;
import com.mue.enums.Role;
import com.mue.enums.UserStatus;
import com.mue.core.exception.UserNotFoundException;
import com.mue.security.domain.LoginRequest;
import com.mue.security.domain.RegisterRequest;
import com.mue.security.domain.AuthResponse;
import com.mue.security.domain.PrincipleResponse;
import com.mue.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;


    @Override
    public void sendConfirmationEmail(String email) {

    }

    @Override
    public UserDetails confirmToken(String token) {
        return null;
    }

    @Override
    public PrincipleResponse getUserInfoById(UUID id) {
        User user = findByIdElseThrow(id);
        return PrincipleResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .userStatus(user.getStatus())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .provider(user.getAuthProvider())
                .build();
    }

    @Override
    public PrincipleResponse getPrinciple(UUID id) {
        User user = findByIdElseThrow(id);
        return PrincipleResponse.builder()
                .id(user.getId())
                .role(user.getRole())
                .userStatus(user.getStatus())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .provider(user.getAuthProvider())
                .build();
    }


    @Override
    @Transactional
    public UserPrincipal register(RegisterRequest registerRequest) {

        if(userRepository.existsByEmail(registerRequest.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists");


        User user = User.builder()
                .email(registerRequest.getEmail())
                .status(UserStatus.NEW)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .fullName(registerRequest.getFullName())
                .role(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .emailVerified(Boolean.TRUE)
                .build();
        user = userRepository.save(user);
        sendConfirmationEmail(user.getEmail());

        return UserPrincipal.create(user);
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String jwt = jwtProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return new AuthResponse(jwt, userPrincipal.getIsVerified());
}
    private User findByIdElseThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id : " + id)
                );
    }

}
