package com.ja.muemp3.services;

import com.ja.muemp3.payload.auth.RegisterRequest;
import com.ja.muemp3.payload.auth.UserInfoResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

public interface UserService extends OAuth2UserService<OAuth2UserRequest, OAuth2User>, UserDetailsService {
    UserDetails loadUserById(UUID id);
    UserDetails registerNewUser(RegisterRequest registerRequest);
    void sendConfirmationEmail(String email);
    UserDetails confirmToken(String token);
    UserInfoResponse getUserInfoById(UUID id);

    void addFavouriteSong(UUID userId, UUID songId);
}
