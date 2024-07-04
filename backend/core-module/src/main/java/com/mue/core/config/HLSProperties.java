package com.mue.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hls")
public class HLSProperties {
    private String root;
    private int time;
    private String segmentFileName;
    private String fileName;
}
