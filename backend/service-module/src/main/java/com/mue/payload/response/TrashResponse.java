package com.mue.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TrashResponse {
    private List<FileSystemResponse> files;
    private Boolean hasMore;
}
