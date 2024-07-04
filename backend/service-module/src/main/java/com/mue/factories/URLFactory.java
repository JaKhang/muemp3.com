package com.mue.factories;

import com.mue.core.config.HLSProperties;
import com.mue.core.config.WebProperties;
import com.mue.entities.FileMetadata;
import com.mue.entities.FileSystemMetadata;
import com.mue.enums.Bitrate;
import com.mue.enums.FileType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Component
public class URLFactory {
    private final WebProperties.DomainProperties domains;
    private final HLSProperties hlsProperties;

    public URLFactory(WebProperties webProperties, HLSProperties hlsProperties) {
        this.domains = webProperties.getDomain();
        this.hlsProperties = hlsProperties;
    }


    public String generateUrl(FileMetadata fileMetadata) {
        return domains.getMedia() + "/" + fileMetadata.getPath();
    }

    public String generateThumbnailUrl(FileMetadata fileSystemMetadata) {
        if(fileSystemMetadata.getFileType() == FileType.IMAGE)
            return domains.getMedia() + "/thumbnail/" + fileSystemMetadata.getId() + ".jpg";
        return   null;
    }

    public Map<Bitrate, String> generateHlsUrl(FileMetadata audio) {
        Map<Bitrate, String> map = new HashMap<>();
        for (var bitrate : Bitrate.values()) {
            String url = new StringJoiner("/")
                    .add(domains.getHls())
                    .add(audio.getId().toString())
                    .add(bitrate.toString())
                    .add(hlsProperties.getFileName())
                    .toString();
            map.put(bitrate, url);
        }
        return map;
    }
}
