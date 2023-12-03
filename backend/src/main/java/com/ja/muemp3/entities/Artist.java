package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Artists")
public class Artist extends Auditable{

    /*------------------
          attribute
    --------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String alias;

    @Column
    private Boolean isOfficial;

    @Column
    private Boolean isIndie;


    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Date birthday;

    @Column
    private Integer subscribes;


    /*------------------
            MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlayList playList;

    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;


    /*------------------
            MTM
    --------------------*/
    @ManyToMany
    @JoinTable(
            name = "artists_types",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<ArtistType> types;

    @ManyToMany
    @JoinTable(
            name = "Subscribes",
            joinColumns = @JoinColumn(name = "artistId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> subscribers;

    @ManyToMany(mappedBy = "artists")
    private List<Song> singedSongs;


    /*------------------
            OTM
    --------------------*/
    @OneToMany(mappedBy = "composer")
    private List<Song> composedSongs;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;


}
