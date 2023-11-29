package com.ja.muemp3.payload.album;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class AlbumRequest {
    private String title;
    private String description;
    private String alias;
    private String shortDescription;
    private Boolean isIndie;
    private UUID distributorId;
    private UUID artistId;
    private UUID thumbnailId;
    private Date releaseDate;
}
