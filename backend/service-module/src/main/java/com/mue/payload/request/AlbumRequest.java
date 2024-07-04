package com.mue.payload.request;

import com.mue.enums.AlbumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
@Data
public class AlbumRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    private String description;
    private String shortDescription;

    @NotEmpty
    private Collection<UUID> artistIds = new ArrayList<>();
    private Collection<UUID> trackIds = new ArrayList<>();
    @NotEmpty
    private Collection<UUID> genreIds = new ArrayList<>();

    @NotNull
    private UUID imageId;
    private UUID distributorId;
    @NotNull
    private AlbumType type;
    private Date releaseDate;
    private boolean isIndie;


}
