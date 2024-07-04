package com.mue.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class FileTypeNotAllowException extends RuntimeException {
    public FileTypeNotAllowException(String message) {
        super(message);
    }
}
