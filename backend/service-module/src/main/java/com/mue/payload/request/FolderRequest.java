package com.mue.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FolderRequest {
    @NotEmpty(message = "Folder parent id is not empty")
    private UUID parentId;

    @NotNull(message = "Folder name is not null")
    private String name;
}
