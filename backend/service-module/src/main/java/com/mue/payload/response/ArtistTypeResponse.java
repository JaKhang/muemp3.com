package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class ArtistTypeResponse {
    private UUID id;
    private String name;
}
