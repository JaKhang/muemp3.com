package com.mue.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    public static final String ACCESS_DENIED = "accessDenied";
    public static final String AUTHENTICATION_REQUIRED = "authenticationRequired";
    private String message;
    private String code;
    private int status;



    public ErrorResponse(String code, HttpStatus status) {
        this.code = code;
        this.status = status.value();
    }

    public ErrorResponse(String message, String code, int status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public static final String RESOURCE_NOTFOUND = "resourceNotFound";
    public static final String INTERNAL_SERVER_ERROR = "" ;
    public static final String BAD_CREDENTIALS = "badCredentials";
    public static final String USERNAME_NOTFOUND = "usernameNotFound";
    public static final String ACCOUNT_LOCKED = "accountLocked";
    public static final String EXPIRED = "accountExpired";
    public static final String UNAUTHORIZED = "unauthorized";
    public static final String DISABLE_ACCOUNT = "disableAccount";
}
