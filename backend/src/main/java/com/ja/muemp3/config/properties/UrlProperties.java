package com.ja.muemp3.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "url-pattern")
public class UrlProperties {
    private String googleImage;
    private String googleAudioStreaming;
    private String confirmationUrl;
}
