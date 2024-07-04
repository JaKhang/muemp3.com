package com.mue.security.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
            System.out.println(authException.getClass());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            ObjectMapper mapper = new ObjectMapper();
//            RestBody<?> apiResponse = new RestBody<>("Unauthorized: " + authException.getMessage(), new ErrorResponse(ErrorResponse.UNAUTHORIZED, HttpStatus.UNAUTHORIZED));
//            response.setStatus(401);
//            OutputStream responseStream = response.getOutputStream();
//            mapper.writeValue(responseStream, apiResponse);
//            responseStream.flush();
            resolver.resolveException(request, response, null, authException);
        }
    @Qualifier("handlerExceptionResolver")
    @Autowired
    private  HandlerExceptionResolver resolver;
}
