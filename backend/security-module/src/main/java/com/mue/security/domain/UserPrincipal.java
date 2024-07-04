package com.mue.security.domain;


import com.mue.entities.User;
import com.mue.enums.UserStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Data
@Builder
public class UserPrincipal implements UserDetails, OAuth2User {
    private UUID id;
    private String email;
    private Boolean isVerified;
    private String fullName;
    private String password;
    private UserStatus userStatus;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;


    public static UserPrincipal create(User user){
        return UserPrincipal.builder()
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().name())))
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .userStatus(user.getStatus())
                .fullName(user.getFullName())
                .isVerified(user.getEmailVerified())
                .build();
    }

   public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getName() {
        return fullName;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Objects.equals(userStatus, UserStatus.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(userStatus, UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !Objects.equals(userStatus, UserStatus.ENABLED) && isVerified;
    }
}
