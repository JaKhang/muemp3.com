package com.mue.payload.response;

import com.mue.enums.AlbumType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
public class AlbumResponse {
    private UUID id;
    private String alias;
    private String name;
    private String description;
    private String shortDescription;
    private String thumbnail;
    private ImageResponse image;
    private AlbumType albumType;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private ObjectResponse distributor;
    private int numberOfTrack;
    private int duration;
    private boolean liked;
    private Collection<ArtistLineResponse> artists;
    private Collection<TrackLineResponse> tracks;


}
