package com.ja.muemp3.services;

import com.ja.muemp3.entities.Image;
import com.ja.muemp3.entities.constants.StorageType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


import java.util.UUID;

public interface ImageService {


    Image findByIdElseNull(UUID thumbnailLId);


    UUID uploadImage(MultipartFile multipartFile, StorageType storageType, String path);

    UUID uploadImageWithLink(String link, StorageType storageType, String s);


    Resource loadResourceById(UUID id);
}
