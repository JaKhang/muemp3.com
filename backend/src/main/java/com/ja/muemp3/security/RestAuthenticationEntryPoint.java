package com.ja.muemp3.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ja.muemp3.payload.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {


        @Override
        public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            System.out.println("error");
            ObjectMapper mapper = new ObjectMapper();
            ApiResponse<?> apiResponse = ApiResponse.of("Unauthorized: " + authException.getMessage(), false);
            response.setStatus(401);
            OutputStream responseStream = response.getOutputStream();
            mapper.writeValue(responseStream, apiResponse);
            responseStream.flush();

        }
}
