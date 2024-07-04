package com.mue.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Entity
@Table(name = "Playlists")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayList extends AbstractEntity {

    /*------------------
          Attribute
    --------------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;


    @Column(unique = true)
    private String alias;

    @Column
    private Boolean isPrivate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean isOfficial;


    /*------------------
             MTO
    --------------------*/
    @ManyToOne
    @JoinColumn(name = "image_cover_id")
    private FileMetadata coverImage;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;


    /*------------------
            MTM
    --------------------*/
    @ManyToMany()
    @JoinTable( name = "playlists_tracks",
                joinColumns = @JoinColumn(name = "playlist_id"),
                inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> tracks = new HashSet<>();

    @ManyToMany(mappedBy = "likedPlaylists")
    private List<User> likedUsers = new ArrayList<>();


    /*------------------
            OTM
    --------------------*/
    @OneToMany(mappedBy = "playList")
    private List<Artist> artists;

    public Integer getLikes() {
        return Hibernate.size(likedUsers);
    }

    public Integer getNumberOfTrack() {
        return Hibernate.size(tracks);
    }

    public Boolean isLiked(User user) {
        return Hibernate.contains(likedUsers, user);
    }


}