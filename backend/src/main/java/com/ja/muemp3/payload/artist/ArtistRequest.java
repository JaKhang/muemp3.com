package com.ja.muemp3.payload.artist;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class ArtistRequest {
    private String name;
    private String alias;
    private Boolean isOfficial;
    private Boolean isBand;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String description;
    private List<UUID> roleIds;
    private UUID thumbnailId;

}
