package com.mue.payload.request;

import com.mue.enums.TrackStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
@Data
public class TrackRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    private UUID imageId;
    @NotEmpty
    private Collection<UUID> artistIds;
    @NotEmpty
    private Collection<UUID> genreIds;
    private String mvLink;
    private Boolean isIndie;
    private int trackIndex;
    private String description;
    private UUID composerId;
    private UUID audioId;
    private UUID distributorId;
    private UUID albumId;
    private TrackStatus status;
    private String lyrics;
    private Boolean isOfficial;
    private Integer duration;
    private Date releaseDate;
    private boolean process = false;

}
