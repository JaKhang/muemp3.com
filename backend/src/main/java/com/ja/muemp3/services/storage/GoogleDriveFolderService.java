package com.ja.muemp3.services.storage;


import com.google.api.services.drive.model.File;
import com.ja.muemp3.payload.response.GoogleDriveFoldersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleDriveFolderService implements GoogleDriveFolder {

    @Autowired
    GoogleFileManager googleFileManager;

    @Override
    public List<GoogleDriveFoldersResponse> getAllFolder() throws IOException, GeneralSecurityException {

        List<File> files = googleFileManager.listFolderContent("root");
        List<GoogleDriveFoldersResponse> responseList = null;
        GoogleDriveFoldersResponse dto = null;

        if (files != null) {
            responseList = new ArrayList<>();
            for (File f : files) {
                dto = new GoogleDriveFoldersResponse();
//                dto.setId(f.getId());
//                dto.setName(f.getName());
//                dto.setLink("https://drive.google.com/drive/u/3/folders/"+f.getId());
                responseList.add(dto);
            }
        }
        return responseList;
    }

    @Override
    public void createFolder(String folderName) throws Exception {
        String folderId = googleFileManager.getFolderId(folderName);
    }

    @Override
    public void deleteFolder(String id) throws Exception {
        googleFileManager.deleteFileOrFolder(id);
    }
}
