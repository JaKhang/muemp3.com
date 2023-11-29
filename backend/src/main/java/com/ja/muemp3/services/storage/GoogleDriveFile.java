package com.ja.muemp3.services.storage;

import com.google.api.services.drive.model.File;
import com.ja.muemp3.payload.response.GoogleDriveFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleDriveFile {
    List<GoogleDriveFileResponse> getAllFile() throws IOException, GeneralSecurityException;
    void deleteFile(String id) throws Exception;
    String uploadFile(MultipartFile file, String filePath, boolean isPublic);
    File upload(MultipartFile file, String filePath, boolean isPublic);
    void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException;

    String getThumbnailLink(String resourceId);
}