package com.ja.muemp3.services.storage;

import com.ja.muemp3.entities.constants.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface StorageService {


    // return details
    String upload(StorageType storageType, MultipartFile file, String path, boolean isPublic);

    void deletedFile(StorageType storageType, String details);

    void downloadFile(StorageType storageType, String details, OutputStream outputStream);

    String getLinkByResource(String resource, StorageType storageType);
}
