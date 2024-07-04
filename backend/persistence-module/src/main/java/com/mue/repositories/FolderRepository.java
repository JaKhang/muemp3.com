package com.mue.repositories;

import com.mue.entities.FolderMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface FolderRepository extends JpaRepository<FolderMetadata, UUID> {
    Optional<FolderMetadata> findByParent(FolderMetadata parent);
}
