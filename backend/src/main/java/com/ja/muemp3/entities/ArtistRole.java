package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ArtistRoles")
public class ArtistRole {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private List<Artist> artists;
}
