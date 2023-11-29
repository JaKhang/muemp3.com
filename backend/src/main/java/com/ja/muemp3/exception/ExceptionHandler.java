package com.ja.muemp3.exception;

import com.ja.muemp3.payload.response.ApiResponse;
import com.ja.muemp3.payload.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception){
        var apiResponse = exception.getResponse();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServerException.class)
    public ResponseEntity<ApiResponse<?>> handleCommonException(ServerException exception){
        return new ResponseEntity<>(ApiResponse.of(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException exception){
        String errorReason = ErrorResponse.UNAUTHORIZED;
        if(exception instanceof BadCredentialsException ){
            errorReason = ErrorResponse.BAD_CREDENTIALS;
        } else if(exception instanceof DisabledException){
            errorReason = ErrorResponse.DISABLE_ACCOUNT;
        } else if(exception instanceof UsernameNotFoundException){
            errorReason = ErrorResponse.USERNAME_NOTFOUND;
        }

        System.out.println(exception.getClass());

        return ResponseEntity.status(401).body(new ErrorResponse(exception.getMessage(), errorReason));
    }



}
