package com.ja.muemp3.services.storage;



import com.ja.muemp3.payload.response.GoogleDriveFoldersResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleDriveFolder {
    List<GoogleDriveFoldersResponse> getAllFolder() throws IOException, GeneralSecurityException;
    void createFolder(String folderName) throws Exception;
    void deleteFolder(String id) throws Exception;
}