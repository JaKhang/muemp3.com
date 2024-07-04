package com.mue.converters;

import com.mue.entities.FileMetadata;
import com.mue.entities.FileSystemMetadata;
import com.mue.entities.FolderMetadata;
import com.mue.factories.URLFactory;
import com.mue.payload.response.FileResponse;
import com.mue.payload.response.FileSystemResponse;
import com.mue.payload.response.FolderResponse;
import com.mue.core.utilities.UnitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileMapper {

    private final URLFactory urlFactory;

    public FileSystemResponse map(FileSystemMetadata src) {
        if (src instanceof FolderMetadata folderMetadata) {
            return map(folderMetadata);
        } else {
            FileMetadata fileMetadata = (FileMetadata) src;
            return map(fileMetadata);
        }
    }


    public FolderResponse map(FolderMetadata src) {
        UUID parentId = src.getParent() != null ? src.getParent().getId() : null;
        return FolderResponse.builder()
                .id(src.getId())
                .name(src.getName())
                .createdAt(src.getCreatedAt())
                .lastModifiedAt(src.getLastModifiedAt())
                .parentId(parentId)
                .isFolder(true)
                .build();
    }

    public FileResponse map(FileMetadata fileMetadata) {
        UUID parentId = fileMetadata.getParent() != null ? fileMetadata.getParent().getId() : null;
        return FileResponse.builder()
                .id(fileMetadata.getId())
                .name(fileMetadata.getName())
                .createdAt(fileMetadata.getCreatedAt())
                .lastModifiedAt(fileMetadata.getLastModifiedAt())
                .parentId(parentId)
                .isFolder(false)
                .basename(fileMetadata.getBasename())
                .alt(fileMetadata.getAlt())
                .mimeType(fileMetadata.getMimetype())
                .url(urlFactory.generateUrl(fileMetadata))
                .thumbnail(urlFactory.generateThumbnailUrl(fileMetadata))
                .previewUrl(urlFactory.generateUrl(fileMetadata))
                .size(UnitConverter.getSize(fileMetadata.getSize()))
                .type(fileMetadata.getFileType().name())
                .build();
    }

    public List<FolderResponse> map(List<FolderMetadata> folders) {
        return folders.stream().map(this::map).toList();
    }


}
