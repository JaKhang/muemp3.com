package com.ja.muemp3.payload.role;

import lombok.Data;

import java.util.UUID;

@Data
public class ArtistRoleResponse {
    private UUID id;
    private String name;
}
