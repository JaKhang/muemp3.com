package com.mue.payload.response;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DistributorResponse {
    private UUID id;
    private String name;
    private String alias;
    private String thumbnail;
    private ImageResponse image;
}
