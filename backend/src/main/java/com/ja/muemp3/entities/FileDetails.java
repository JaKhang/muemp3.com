package com.ja.muemp3.entities;

import com.ja.muemp3.entities.constants.StorageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class FileDetails extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    /*
    * If storageType is LOCAL then details is file path
    * If storageType is DRIVER ten details is resourceId;
    * ...
    * */
    @Column(length = 2048)
    protected String resource;
    protected StorageType storageType;
    protected String name;
    protected Long size;


}
