package com.mue.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class RestBody<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public RestBody(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public RestBody(T data) {
        this.data = data;
    }
}
