package com.ja.muemp3.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private LocalStorageProperties local;
    private GoogleDriveProperties googleDrive;
    private String defaultType;

    @Data
    public static class LocalStorageProperties {
        private String root;
    }


    @Data
    public static class GoogleDriveProperties {
        private String root;
        private String tokenDirectoryPath;
        private String applicationName;
        private String credentialFilePath;
    }
}
