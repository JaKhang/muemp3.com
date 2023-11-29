package com.ja.muemp3.services.storage;

import com.ja.muemp3.config.properties.StorageProperties;
import com.ja.muemp3.entities.constants.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class DefaultStorageService implements StorageService{

    private final LocalFileService localFileService;
    private final GoogleDriveFile googleDriveFile;
    private final StorageProperties storageProperties;

    @Override
    public String upload(StorageType storageType, MultipartFile file, String path, boolean isPublic) {
        if (storageType == null)
            storageType = StorageType.valueOf(storageProperties.getDefaultType());
        if(StorageType.LOCAL.equals(storageType)){
            return localFileService.store(path, file);
        } else {
            return googleDriveFile.uploadFile(file, path, isPublic);
        }

    }

    @Override
    public void deletedFile(StorageType storageType, String details) {

    }

    @Override
    public void downloadFile(StorageType storageType, String details, OutputStream outputStream) {

    }

    @Override
    public String getLinkByResource(String resource, StorageType storageType) {
        return googleDriveFile.getThumbnailLink(resource);
    }
}
