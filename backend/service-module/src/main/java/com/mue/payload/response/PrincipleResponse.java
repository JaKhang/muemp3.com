package com.mue.payload.response;

import com.mue.enums.AuthProvider;
import com.mue.enums.Role;
import com.mue.enums.UserStatus;

import java.sql.Timestamp;
import java.util.UUID;

public class PrincipleResponse {
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
