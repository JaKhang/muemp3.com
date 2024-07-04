package com.mue.services;


import com.mue.enums.FileType;
import com.mue.payload.request.FolderRequest;
import com.mue.payload.response.ExplorerResponse;
import com.mue.payload.response.FileResponse;
import com.mue.payload.response.TrashResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    UUID upload(MultipartFile file, UUID folderId);

    Resource load(UUID fileId);

    void moveToTrash(UUID uuid);

    void restoreFromTrash(UUID uuid);

    void updateFileSystem(UUID id, FolderRequest request);

     UUID makeACopy(UUID fileID);

    UUID createFolder(UUID parentId, String name);

    ExplorerResponse findFolderChildren(Pageable pageable, UUID folderId , FileType type, String name, boolean deleted);

    void deletedFile(UUID id);

    FileResponse findById(UUID id);

    ExplorerResponse findRootFolder(Pageable pageable , FileType type, String name, boolean deleted);

    void clearTrash();

    TrashResponse findAllInTrash(Pageable pageable, FileType fileType, String name);
}
