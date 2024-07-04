package com.mue.core.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenConfirmedException extends AuthenticationException {
    public TokenConfirmedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenConfirmedException(String msg) {
        super(msg);
    }
}
