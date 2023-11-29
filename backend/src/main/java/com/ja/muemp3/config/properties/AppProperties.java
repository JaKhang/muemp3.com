package com.ja.muemp3.config.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private Auth auth = new Auth();
    private OAuth2 oauth2 = new OAuth2();
    private Storage storage = new Storage();
    private UrlPattern urlPattern = new UrlPattern();


    @Data
    public static class Auth {
        private String secretKey;
        private int expiredTime;


    }

    @Data
    public static class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }


    @Data
    public static class Storage {
        private String rootPath;
        private String googleDriveRoot;
        private Map<String, String> imagePaths;

        public String getImagePath(String key) {
            return imagePaths.get(key);
        }

        public void setImagePath(Map<String, String> imagePath) {
            this.imagePaths = imagePath;
        }
    }


    @Data
    public static class UrlPattern {
        private String googleImage;
        private String googleAudioStreaming;
        private String confirmationUrl;
    }

}
