package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ArtistLineResponse {
    private UUID id;
    private String name;
    private String alias;
    private String thumbnail;
    private int numberOfTracks;
    private int likes;
    private boolean liked;

}
