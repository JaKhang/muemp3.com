package com.mue.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class GenreRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String alias;
    private UUID imageId;

    private String color;
}
