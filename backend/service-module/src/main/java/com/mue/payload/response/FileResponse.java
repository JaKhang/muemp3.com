package com.mue.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class FileResponse extends FileSystemResponse{
    private String thumbnail;
    private String type;
    private String mimeType;
    private String alt;
    private String url;
    private String size;
    private String basename;
    private String icon;
    private String previewUrl;
    private boolean isFolder = false;
}
