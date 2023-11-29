package com.ja.muemp3.payload.genre;

import lombok.Data;

import java.util.UUID;

@Data
public class GenreResponse {
    private UUID id;
    private String name;
    private String thumbnail;
}
