package com.mue.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.UUID;

import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Getter
@Setter
@Table(name = "file_system_metadata")
@Inheritance(strategy=JOINED)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = {"parent"})
public abstract class FileSystemMetadata extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected boolean isFolder = false;

    @Column(nullable = false)
    protected boolean inTrash = false;


    /*------------------
          @ManyToOne
    --------------------*/
    @ManyToOne
    @JoinColumn(name="parent_id")
    protected FolderMetadata parent;





}