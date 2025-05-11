package com.mue.payload.response;

import com.mue.enums.AlbumType;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class AlbumLineResponse {
    private UUID id;
    private String name;
    private String alias;
    private AlbumType albumType;
    private String thumbnail;
    private Collection<ObjectResponse> artists;
    private Collection<ObjectResponse> genres;
    private Integer numberOfTrack;
    private Date releaseDate;
    private String shortDescription;
    private Integer duration;
    private boolean liked;

}
