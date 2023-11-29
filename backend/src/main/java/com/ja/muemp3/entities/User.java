package com.ja.muemp3.entities;


import com.ja.muemp3.entities.constants.AuthProvider;
import com.ja.muemp3.entities.constants.Role;
import com.ja.muemp3.entities.constants.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@Entity
@Getter @Setter
public class User extends Auditable{
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
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> favouriteSongs = new ArrayList<>();

    @ManyToMany(mappedBy = "subscribers")
    private List<Artist> subscribed;

    @OneToMany(mappedBy = "createdBy")
    private List<PlayList> playLists;

    @OneToMany(mappedBy = "createdBy")
    private List<Song> createdBy;

    @OneToMany(mappedBy = "user")
    private List<ConfirmationToken> tokens;


}


