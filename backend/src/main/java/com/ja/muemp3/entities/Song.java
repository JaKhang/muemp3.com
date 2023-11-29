package com.ja.muemp3.entities;

import com.ja.muemp3.entities.constants.SongStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Songs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private Boolean isOfficial;
    private Integer duration;
    private Date releaseDate;
    private String mvLink;
    private Boolean isIndie;

    @Column(unique = true)
    private String alias;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated
    private SongStatus status;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @ManyToOne
    @JoinColumn(name = "thumbnail_id")
    private Image thumbnail;


    @ManyToOne
    @JoinColumn(name = "composer_id")
    private Artist composer;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private PlayerCounter playerCounter;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "song")
    private Audio audio;


    @ManyToMany
    @JoinTable(
            name = "singer_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(
            name = "genre_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "album_song",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "alumn_id")
    )
    private List<Album> albums;


    @ManyToMany(mappedBy = "songs")
    private List<PlayList> playLists;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song song)) return false;
        return getId().equals(song.getId());
    }

}
