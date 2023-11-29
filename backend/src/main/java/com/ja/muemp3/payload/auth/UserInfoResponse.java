package com.ja.muemp3.payload.auth;

import com.ja.muemp3.entities.constants.AuthProvider;
import com.ja.muemp3.entities.constants.Role;
import com.ja.muemp3.entities.constants.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Data
public class UserInfoResponse {
    private UUID id;
    private String email;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private Role role;
    private UserStatus userStatus;
    private String fullName;
    private AuthProvider provider;
    private String avatar;
}
