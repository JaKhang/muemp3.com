package com.mue.repositories;


import com.mue.entities.FileMetadata;
import com.mue.enums.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileMetadata, UUID> {


    Optional<FileMetadata> findByIdAndFileType(UUID id, FileType fileType);
}
