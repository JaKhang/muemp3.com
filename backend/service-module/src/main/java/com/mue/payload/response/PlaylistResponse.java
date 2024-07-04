package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PlaylistResponse {
    private UUID id;
    private String alias;
    private String name;
    private String owner;
    private String thumbnail;
    private ImageResponse image;
    private Integer totalTrack;
    private Boolean liked;
    private Integer likes;

}
