package com.ja.muemp3.factories;

import com.ja.muemp3.config.properties.UrlProperties;
import com.ja.muemp3.entities.Image;
import com.ja.muemp3.entities.constants.StorageType;
import com.ja.muemp3.entities.constants.ThumbnailSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LinkFactory {

    private final UrlProperties urlProperties;

    public String createThumbnailLink(Image image, ThumbnailSize thumbnailSize){
        if(Objects.equals(image.getStorageType(), StorageType.GOOGLE_DRIVE)){
            return image.getLink() + "=s" + thumbnailSize.size;
        } else {
            return image.getLink();
        }

    }




    public String createConfirmationUrl(String token){
        return String.format(urlProperties.getConfirmationUrl(), token);
    }
}
