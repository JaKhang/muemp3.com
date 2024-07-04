package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class GenreResponse {
    private UUID id;
    private String name;
    private String alias;
    private ImageResponse imageResponse;
    private String thumbnail;
    private String color;
}
