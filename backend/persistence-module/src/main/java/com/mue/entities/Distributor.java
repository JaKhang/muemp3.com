package com.mue.entities;

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
public class Distributor extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String alias;

    @ManyToOne()
    @JoinColumn(name = "cover_image_id")
    private FileMetadata coverImage;

    @OneToMany(mappedBy = "distributor")
    private List<Track> tracks;

    @OneToMany(mappedBy = "distributor")
    private List<Album> albums;
}
