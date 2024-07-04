package com.mue.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Artists")
@SQLRestriction("deleted <> true")
public class Artist extends AbstractEntity {

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

    @Column
    private Boolean isBand;


    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Date birthday;




    /*------------------
            MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private PlayList playList;

    @ManyToOne
    @JoinColumn(name = "avatar_id")
    private FileMetadata avatar;

    @ManyToOne
    @JoinColumn(name = "cover_image_id")
    private FileMetadata coverImage;


    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

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


    @ManyToMany(mappedBy = "likedArtists")
    private List<User> likedUsers = new ArrayList<>();


    @ManyToMany(mappedBy = "artists")
    private List<Track> singedTracks;


    /*------------------
            OTM
    --------------------*/
    @OneToMany(mappedBy = "composer")
    private List<Track> composedTracks;

    @ManyToMany(mappedBy = "artists")
    private List<Album> albums;


    public int countLikes(){
        return Hibernate.size(this.likedUsers);
    }

    public boolean isLike(User user){
        if (user == null)
            return false;
        return Hibernate.contains(likedUsers, user);
    }

    public int countTracks() {

        return Hibernate.size(singedTracks);
    }
}
