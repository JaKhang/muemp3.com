package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class TrackResponse {
    private UUID id;
    private String name;
    private String alias;
    private List<ArtistLineResponse> artists;
    private List<ObjectResponse> genres;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private Date releaseDate;
    private ObjectResponse distributor;
    private ObjectResponse composer;
    private String lyrics;
    private String description;
    private ImageResponse image;
    private ObjectResponse album;
    private FileResponse audio;
    private String mvLink;
    private Boolean isIndie;
    private Integer duration;
    private Boolean isOfficial;
    private Integer index;
    private Boolean isLike;
}
