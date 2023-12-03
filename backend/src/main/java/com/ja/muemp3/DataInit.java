package com.ja.muemp3;

import com.ja.muemp3.entities.ArtistType;
import com.ja.muemp3.entities.User;
import com.ja.muemp3.entities.constants.AuthProvider;
import com.ja.muemp3.entities.constants.Role;
import com.ja.muemp3.repositories.ArtistTypeRepository;
import com.ja.muemp3.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArtistTypeRepository artistTypeRepository;

//    @PostConstruct
//    private void postConstruct() {
//        User admin = User.builder()
//                .authProvider(AuthProvider.LOCAL)
//                .password(passwordEncoder.encode("muemusic"))
//                .email("muemusic@gmail.cm")
//                .password("muemusic")
//                .emailVerified(true)
//                .fullName("Mue Mp3")
//                .role(Role.ADMIN)
//                .build();
//        userRepository.save(admin);
//
//
//        artistTypeRepository.save(new ArtistType(UUID.randomUUID(), "Ca sĩ"));
//        artistTypeRepository.save(new ArtistType(UUID.randomUUID(), "Nhạc sĩ"));
//        artistTypeRepository.save(new ArtistType(UUID.randomUUID(), "Phạc công"));
//        artistTypeRepository.save(new ArtistType(UUID.randomUUID(), "Producer"));
//    }
}
