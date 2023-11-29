package com.ja.muemp3.payload.album;

import com.ja.muemp3.payload.artist.ArtistResponse;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class AlbumLineResponse {
    private UUID id;
    private String title;
    private String description;
    private String thumbnail;
    private String alias;
    private Date releaseDate;
    private ArtistResponse artist;
}
