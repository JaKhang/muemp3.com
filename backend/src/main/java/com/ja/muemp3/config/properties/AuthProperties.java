package com.ja.muemp3.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    private Oauth2Properties oauth2;
    private JWTProperties jwt;

    @Data
    public static class Oauth2Properties{

        private List<String> successRedirectUris;
        private List<String> failedRedirectUris;

    }

    @Data
    public static class JWTProperties{
        private String secretKey;
        private Integer expiredTime;
    }

}





