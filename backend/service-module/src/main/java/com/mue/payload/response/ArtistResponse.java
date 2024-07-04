package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ArtistResponse {
    private UUID id;
    private String thumbnail;
    private String alias;
    private String avatarUrl;
    private String coverUrl;
    private String name;
    private String countryName;
    private Date birthday;
    private Integer likes;
    private String description;
    private List<String> typeNames;
    private List<ArtistTypeResponse> types;
    private ObjectResponse country;
    private ImageResponse coverImage;
    private ImageResponse avatarImage;
    private Timestamp createdAt;
    private Timestamp lastModifiedAt;
    private boolean liked;

}
