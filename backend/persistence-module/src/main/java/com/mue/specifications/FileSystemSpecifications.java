package com.mue.specifications;

import com.mue.core.domain.QueryRequest;
import com.mue.entities.FileSystemMetadata;
import com.mue.entities.FolderMetadata;
import com.mue.enums.FileType;
import org.springframework.data.jpa.domain.Specification;

import static com.mue.core.domain.QueryRequest.EQUAL;

public class FileSystemSpecifications {

    public static Specification<FileSystemMetadata> nameLike(String name){
        return (root, query, builder) -> name == null ? builder.conjunction() : builder.like(root.get("name"),"%" +name +"%");
    }

    public static Specification<FileSystemMetadata> deletedIs(boolean deleted){
        return (root, query, builder) -> builder.equal(root.get("deleted"), deleted);
    }

    public static Specification<FileSystemMetadata> typeIs(FileType type){
        return (root, query, builder) -> type == null ? builder.conjunction() : builder.equal(root.get("fileType"), type);
    }

    public static Specification<FileSystemMetadata> parentEquals(FolderMetadata folderMetadata){
        return (root, query, builder) -> builder.equal(root.get("parent"), folderMetadata);

    }

    public static Specification<FileSystemMetadata> inTrash(){
        return (root, query, builder) -> builder.isTrue(root.get("inTrash"));

    }
}
