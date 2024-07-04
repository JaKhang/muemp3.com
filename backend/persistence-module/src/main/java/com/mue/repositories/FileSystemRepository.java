package com.mue.repositories;


import com.mue.entities.FileSystemMetadata;
import com.mue.entities.FolderMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileSystemRepository extends JpaRepository<FileSystemMetadata, UUID>, JpaSpecificationExecutor<FileSystemMetadata> {


    @Query("SELECT f  from FileSystemMetadata f WHERE f.id = :uuid and f.deleted = true")
    Optional<FileSystemMetadata> findInTrashById(UUID uuid);

    int countByNameAndParent(String name, FolderMetadata parent);


    boolean existsByNameAndParent(String name, FolderMetadata parent);


}
