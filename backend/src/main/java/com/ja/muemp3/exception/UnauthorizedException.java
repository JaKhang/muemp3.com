package com.ja.muemp3.exception;


import com.ja.muemp3.payload.response.ApiResponse;

public class UnauthorizedException extends RuntimeException{


    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public ApiResponse<?> getResponse() {
        return apiResponse;
    }

    private void setApiResponse() {
        apiResponse = new ApiResponse<>(this.getMessage(), Boolean.FALSE);
    }

    private transient ApiResponse<?> apiResponse;
}
