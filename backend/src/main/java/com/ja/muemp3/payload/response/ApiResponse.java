package com.ja.muemp3.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String DEFAULT_CREATED_MESSAGE = "Resource was created !";
    private static String DEFAULT_MESSAGE = "Success";
    private static boolean DEFAULT_SUCCESS = Boolean.TRUE;
    private String message;
    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public static <T>ApiResponse<T> of(String message, boolean success, T data){
        return new ApiResponse<>(message, success, data);
    }

    public static <T>ApiResponse<T> of(String message, boolean success){
        return new ApiResponse<>(message, success);
    }

    public static <T>ApiResponse<T> ok(String message){
        return new ApiResponse<>(message, DEFAULT_SUCCESS);
    }

    public static <T>ApiResponse<T> ok(){
        return new ApiResponse<>(DEFAULT_MESSAGE, DEFAULT_SUCCESS);
    }

    public static <T>ApiResponse<T> ok(T data){
        return new ApiResponse<>(DEFAULT_MESSAGE, DEFAULT_SUCCESS, data);
    }

    public static <T>ApiResponse<T> created(T data){
        return new ApiResponse<>(DEFAULT_CREATED_MESSAGE, DEFAULT_SUCCESS, data);

    }
}
