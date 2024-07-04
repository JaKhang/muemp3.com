package com.mue.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "folder_metadata")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FolderMetadata extends FileSystemMetadata{

    @OneToMany(mappedBy = "parent")
    List<FileSystemMetadata> children = new ArrayList<>(0);

    @Override
    public String toString() {
        return "FolderMetadata{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFolder=" + isFolder +
                '}';
    }
}
