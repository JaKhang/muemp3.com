package com.mue.core.exception;

import org.springframework.security.core.AuthenticationException;

public class ConfirmationTokenInvalidException extends AuthenticationException {

    public ConfirmationTokenInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ConfirmationTokenInvalidException(String msg) {
        super(msg);
    }
}
