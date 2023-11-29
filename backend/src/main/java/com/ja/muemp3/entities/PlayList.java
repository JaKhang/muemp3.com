package com.ja.muemp3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "Playlists")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayList extends Auditable{

    /*------------------
          Attribute
    --------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private Date releaseDate;

    @Column(unique = true)
    private String alias;

    @Column
    private Boolean isPrivate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;


    /*------------------
             MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;


    /*------------------
            MTM
    --------------------*/
    @ManyToMany
    @JoinTable( name = "PlaylistsSongs",
                joinColumns = @JoinColumn(name = "playlistId"),
                inverseJoinColumns = @JoinColumn(name = "songId")
    )
    private List<Song> songs;

    /*------------------
            OTM
    --------------------*/
    @OneToMany(mappedBy = "playList")
    private List<Artist> artists;

}