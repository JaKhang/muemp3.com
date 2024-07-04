package com.mue.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ArtistRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotNull
    private Boolean isOfficial;
    @NotNull
    private Boolean isBand;
    @NotNull
    private Boolean isIndie;
    @NotNull
    private Date birthday;
    private String description;
    @NotEmpty
    private List<UUID> typeIds;

    private UUID avatarId;

    private UUID coverId;

    private UUID countryId;
}
