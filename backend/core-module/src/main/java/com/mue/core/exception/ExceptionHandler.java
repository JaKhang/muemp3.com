package com.mue.core.exception;


import com.mue.core.domain.ErrorResponse;
import com.mue.core.domain.RestBody;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DESCRIPTION_TEMPLATE = "Exception class: %s -- Request path: %s";

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), ErrorResponse.RESOURCE_NOTFOUND, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(FileSizeLimitExceededException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), ErrorResponse.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        var apiResponse = new RestBody<>(exception.getMessage(), errorResponse);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AuthenticationException.class, UnauthorizedException.class})
    public ResponseEntity<?> handleAuthenticationException(Exception exception) {
        System.out.println(exception.getClass());
        String errorCode = ErrorResponse.UNAUTHORIZED;

        if (exception instanceof BadCredentialsException) {
            errorCode = ErrorResponse.BAD_CREDENTIALS;
        } else if (exception instanceof DisabledException) {
            errorCode = ErrorResponse.DISABLE_ACCOUNT;
        } else if (exception instanceof UsernameNotFoundException) {
            errorCode = ErrorResponse.USERNAME_NOTFOUND;
        }


        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), errorCode, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(401).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponseRes = new ErrorResponse(exception.getMessage(), ErrorResponse.USERNAME_NOTFOUND, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(401).body(errorResponseRes);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> handleInsufficientAuthenticationException(InsufficientAuthenticationException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), ErrorResponse.AUTHENTICATION_REQUIRED, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ErrorResponse.ACCESS_DENIED, HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({MethodNotImplementException.class})
    public ResponseEntity<Object> handleCommonException(RuntimeException exception) {
        return handleExceptionInternal(exception, null, null, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ErrorResponse.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        System.out.println(ex);
        System.out.println(body);
        return ResponseEntity.internalServerError().body(errorResponse);
    }


    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleResourceNotFound(ex);
    }

}
