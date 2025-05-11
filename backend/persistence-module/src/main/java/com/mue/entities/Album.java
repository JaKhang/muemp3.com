package com.mue.entities;

import com.mue.enums.AlbumType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@SuperBuilder
@Table(name = "Albums")
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted <> true")
public class Album extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

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

    @Column
    @Enumerated(EnumType.STRING)
    private AlbumType type;




    /*------------------
            MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "cover_image_id")
    private FileMetadata coverImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "albums_artists",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> artists = new ArrayList<>(0);


    @ManyToMany(mappedBy = "likedAlbums")
    private List<User> likedUsers = new ArrayList<>(0);


    @JoinTable(
            name = "albums_genres",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "genres_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("createdAt")
    private List<Genre> genres = new ArrayList<>(0);

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distributor_id")
    private Distributor distributor;

    /*------------------
            OTM
    --------------------*/
    @OneToMany(mappedBy = "album")
    @OrderBy("trackIndex")
    private List<Track> tracks = new ArrayList<>(0);

    public int getNumberOfTracks(){
        return Hibernate.size(tracks);
    }

    public boolean isLiked(User user) {
        return Hibernate.contains(likedUsers, user);
    }

    public int getDuration(){
        return 0;
    }
}
