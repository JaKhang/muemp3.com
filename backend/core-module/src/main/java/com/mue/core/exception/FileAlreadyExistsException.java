package com.mue.core.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException(String string) {
        super(string);
    }
}
