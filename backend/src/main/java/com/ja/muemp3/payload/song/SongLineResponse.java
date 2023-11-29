package com.ja.muemp3.payload.song;

import com.ja.muemp3.payload.album.AlbumLineResponse;
import com.ja.muemp3.payload.artist.ArtistResponse;
import com.ja.muemp3.payload.genre.GenreResponse;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
public class SongLineResponse {
    private UUID id;
    private String alias;
    private String title;
    private String thumbnail;
    private String src;
    private Integer listens;
    private Integer duration;
    private Timestamp releaseDate;
    private List<GenreResponse> genres;
    private AlbumLineResponse album;
    private Boolean liked;
    private Timestamp createdAt;
    private Timestamp LastModifiedAt;
    private List<ArtistResponse> artists;


}
