package com.ja.muemp3.payload.genre;

import lombok.Data;

import java.util.UUID;

@Data
public class GenreRequest {
    private String name;
    private UUID thumbnailId;
    private String alias;
}
