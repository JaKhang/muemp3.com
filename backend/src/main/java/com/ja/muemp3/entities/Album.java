package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@Table(name = "Albums")
@AllArgsConstructor
@NoArgsConstructor
public class Album extends Auditable{

    /*------------------
          Attributes
    --------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String title;

    @Column
    private Date releaseDate;

    @Column(unique = true)
    private String alias;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column
    private Boolean isIndie;

    /*------------------
            MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    /*------------------
            OTM
    --------------------*/
    @ManyToMany(mappedBy = "albums")
    private List<Song> songs;


}
