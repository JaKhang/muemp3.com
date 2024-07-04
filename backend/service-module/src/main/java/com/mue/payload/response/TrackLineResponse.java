package com.mue.payload.response;


import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Builder
@Data
public class TrackLineResponse {
    private UUID id;
    private String name;
    private String alias;
    private String thumbnail;
    private ObjectResponse album;
    private Collection<ObjectResponse> artists;
    private Collection<ObjectResponse> genres;
    private int duration;
    private boolean liked;
    private ImageResponse image;

}
