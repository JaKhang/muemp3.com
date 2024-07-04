package com.mue.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.UUID;
@Data
@SuperBuilder
public abstract class FileSystemResponse {
    protected UUID id;
    protected UUID parentId;
    protected String name;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    protected Timestamp createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    protected Timestamp lastModifiedAt;
}
