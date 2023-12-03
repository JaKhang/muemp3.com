package com.ja.muemp3.entities;

import com.ja.muemp3.entities.constants.StorageType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "Images")
@Getter
@Setter
@SuperBuilder
@ToString
public class Image extends FileDetails {

    private String link;

    public Image() {
        super();
    }



}