package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ImageResponse {
    private UUID id;
    private int height;
    private int width;
    private String url;
    private String thumbnail;
}
