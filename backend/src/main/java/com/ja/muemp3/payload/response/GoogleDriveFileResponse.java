package com.ja.muemp3.payload.response;

import lombok.Data;

@Data
public class GoogleDriveFileResponse {
    private String id;
    private String name;
    private String thumbnailLink;
    private String size;
}
