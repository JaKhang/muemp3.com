package com.ja.muemp3.payload.song;

import com.ja.muemp3.entities.constants.SongStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class SongRequest {
    private String title;
    private String alias;
    private List<UUID> artistIds;
    private List<UUID> genreIds;
    private String description;
    private UUID thumbnailId;
    private UUID albumId;
    private UUID composerId;
    private UUID distributorId;
    private Integer duration;
    private String lyrics;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private SongStatus status;
    private MultipartFile source;
    private Boolean isIndie = false;
    private Boolean isOfficial = true;
    private String mvLink;
}
