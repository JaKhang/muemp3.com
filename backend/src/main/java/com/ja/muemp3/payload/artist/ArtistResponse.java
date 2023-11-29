package com.ja.muemp3.payload.artist;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ArtistResponse {
    private UUID id;
    private String name;
    private String alias;
    private String thumbnail;
    private String thumbnailLG;
    private String thumbnailMD;
    private Long createdAt;
    private Long lastModifiedAt;
}
