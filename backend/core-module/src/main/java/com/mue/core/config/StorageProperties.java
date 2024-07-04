package com.mue.core.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private LocalStorageProperties local;
    private GoogleDriveProperties googleDrive;
    private String defaultType;
    private int thumbnailSize;

    @Data
    public static class LocalStorageProperties {
        private String rootDir;
        private String thumbnailDir;
        private String trashDir;
    }


    @Data
    public static class GoogleDriveProperties {
        private String root;
        private String tokenDirectoryPath;
        private String applicationName;
        private String credentialFilePath;
    }
}
