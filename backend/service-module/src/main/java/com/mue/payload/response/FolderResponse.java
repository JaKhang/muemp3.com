package com.mue.payload.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class FolderResponse extends FileSystemResponse {
    private Boolean isFolder = Boolean.FALSE;
}
