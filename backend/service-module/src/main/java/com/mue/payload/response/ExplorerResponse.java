package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExplorerResponse {
    private List<FileSystemResponse> files;
    private List<FolderResponse> parents;
    private FolderResponse current;
    private Boolean hasMore;
}
