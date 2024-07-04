package com.mue.entities;

import com.mue.enums.TrackStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Tracks")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Boolean isOfficial;
    private Integer duration;
    private Date releaseDate;
    private String mvLink;
    private Boolean isIndie;
    private int trackIndex;

    @Column(unique = true)
    private String alias;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated
    private TrackStatus status;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @ManyToOne
    @JoinColumn(name = "cover_image_id")
    private FileMetadata coverImage;


    @ManyToOne
    @JoinColumn(name = "composer_id")
    private Artist composer;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToOne(mappedBy = "track", cascade = CascadeType.ALL)
    private PlayerCounter playerCounter;

    @ManyToOne
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;


    @ManyToOne
    @JoinColumn(name = "audio_file_id")
    private FileMetadata audio;


    @ManyToMany
    @JoinTable(
            name = "singers_tracks",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id")
    )
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(
            name = "genres_tracks",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;


    @ManyToMany(mappedBy = "tracks")
    private List<PlayList> playLists;


    @ManyToMany
    @JoinTable(
            name = "liked_tracks",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users ;


    public boolean isLike(User user) {
        return Hibernate.contains(users, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track track)) return false;
        return getId().equals(track.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
