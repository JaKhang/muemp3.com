package com.mue.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;
@Data
public class ObjectResponse {
        private UUID id;
        private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String alias;
}
