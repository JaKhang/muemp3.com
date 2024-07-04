package com.mue.entities;

import com.mue.enums.FileType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.io.FilenameUtils;

import static jakarta.persistence.InheritanceType.JOINED;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Getter
@Setter
@Table(name = "file_metadata")
@SuperBuilder
@AllArgsConstructor
@Inheritance(strategy=SINGLE_TABLE)
@NoArgsConstructor
public class FileMetadata extends FileSystemMetadata{
    private String alt;

    @Column(nullable = false)
    protected String mimetype;

    @Column(nullable = false)
    protected FileType fileType;

    @Column(nullable = false)
    protected Long size = 0L;

    @Column
    protected String path;


    public String getBasename() {
        return FilenameUtils.getBaseName(name);
    }

    @Override
    public String toString() {
        return "FileMetadata{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFolder=" + isFolder +
                '}';
    }
}
