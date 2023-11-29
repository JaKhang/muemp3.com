package com.ja.muemp3.payload.response;

import lombok.Data;

import java.util.UUID;
@Data
public class ImageResponse {
    private UUID id;
    private Long size;
    private String resourceId;
    private String name;
}
