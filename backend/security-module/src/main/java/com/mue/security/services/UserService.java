package com.mue.security.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

public interface UserService extends OAuth2UserService<OAuth2UserRequest, OAuth2User>, UserDetailsService {


    UserDetails loadUserById(UUID id);


}
