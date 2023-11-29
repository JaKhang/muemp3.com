package com.ja.muemp3.payload.artist;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ArtistDetailsResponse {
    private UUID id;
    private String thumbnail;
    private String name;
    private Date birthday;
    private Integer subscribes;
    private String description;
    private List<String> roles;
}
