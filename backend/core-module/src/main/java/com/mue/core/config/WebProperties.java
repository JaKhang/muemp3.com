package com.mue.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "web")
public class WebProperties {
    private CorsProperties cors;
    private DomainProperties domain;

    @Data
    public static class CorsProperties{

        private String[] allowedOrigins;
    }


    @Data
    public static class DomainProperties{
        private String web;
        private String media;
        private String api;
        private String hls;
    }
}
