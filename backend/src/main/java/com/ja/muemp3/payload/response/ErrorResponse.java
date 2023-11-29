package com.ja.muemp3.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String reason;

    public static String BAD_CREDENTIALS = "badCredentials";
    public static String USERNAME_NOTFOUND = "usernameNotFound";
    public static String ACCOUNT_LOCKED = "accountLocked";
    public static String EXPIRED = "accountExpired";
    public static String UNAUTHORIZED = "unauthorized";
    public static final String DISABLE_ACCOUNT = "disableAccount";
}
