package com.mue.entities;



import com.mue.enums.AuthProvider;
import com.mue.enums.Role;
import com.mue.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@Entity
@Getter @Setter
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 2048)
    private String avatar;

    @Column(length = 64, nullable = false)
    private String fullName;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column
    private Boolean emailVerified = false;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column
    private String providerId;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "liked_tracks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> likedTracks = new HashSet<>();

    @ManyToMany
    @JoinTable( name = "liked_playlists",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<PlayList> likedPlaylists = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "liked_artists",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Artist> likedArtists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "liked_albums",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Album> likedAlbums = new HashSet<>();


    @OneToMany(mappedBy = "createdBy")
    private List<PlayList> playLists;

    @OneToMany(mappedBy = "createdBy")
    private List<Track> createdBy;

    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> tokens;


    @Override
    public String getName() {
        return fullName;
    }
}


