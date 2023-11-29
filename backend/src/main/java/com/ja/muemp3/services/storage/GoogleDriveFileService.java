package com.ja.muemp3.services.storage;


import com.google.api.services.drive.model.File;

import com.ja.muemp3.payload.response.GoogleDriveFileResponse;
import com.ja.muemp3.utils.ConvertByteToMB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleDriveFileService implements GoogleDriveFile {

    private final GoogleFileManager googleFileManager;



    @Override
    public void deleteFile(String id) {
        try {
            googleFileManager.deleteFileOrFolder(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public String uploadFile(MultipartFile file, String filePath, boolean isPublic) {
        return upload(file, filePath, isPublic).getId();
    }

    @Override
    public File upload(MultipartFile file, String filePath, boolean isPublic) {
        String type = "";
        String role = "";
        if (isPublic) {
            // Public file of folder for everyone
            type = "anyone";
            role = "reader";
        } else {
            // Private
            type = "private";
            role = "private";
        }
        return googleFileManager.upload(file, filePath, type, role);
    }

    @Override
    public void downloadFile(String id, OutputStream outputStream) throws IOException, GeneralSecurityException {
        googleFileManager.downloadFile(id, outputStream);
    }

    @Override
    public String getThumbnailLink(String resourceId) {
        try {
            System.out.println(resourceId);
            File file = googleFileManager.findFileBy(resourceId);
            System.out.println(file);
            return file.getThumbnailLink().split("=")[0];
        } catch (IOException | GeneralSecurityException e) {
            throw new StorageFileNotFoundException();
        }
    }

    @Override
    public List<GoogleDriveFileResponse> getAllFile() throws IOException, GeneralSecurityException {

        List<GoogleDriveFileResponse> responseList = null;
        List<File> files = googleFileManager.listEverything();
        GoogleDriveFileResponse dto = null;

        if (files != null) {
            responseList = new ArrayList<>();
            for (File f : files) {
                dto = new GoogleDriveFileResponse();
                if (f.getSize() != null) {

                    dto.setId(f.getId());
                    dto.setName(f.getName());
                    dto.setThumbnailLink(f.getThumbnailLink());
                    dto.setSize(ConvertByteToMB.getSize(f.getSize()));
//                    dto.setLink("https://drive.google.com/file/d/" + f.getId() + "/view?usp=sharing");
//                    dto.setShared(f.getShared());
                    System.out.println(f.getId());
                    responseList.add(dto);
                }
            }
        }
        return responseList;
    }
}
