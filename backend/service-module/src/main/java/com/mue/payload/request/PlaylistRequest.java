package com.mue.payload.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlaylistRequest {
    private String name;
    private String alias;
    private UUID imageId;
    private String description;
    private boolean isPublic;
    private boolean isOfficial;
}
