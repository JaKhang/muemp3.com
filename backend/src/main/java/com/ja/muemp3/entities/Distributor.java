package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Distributors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "distributor")
    private List<Song> songs;

    @OneToMany(mappedBy = "distributor")
    private List<Album> albums;
}
