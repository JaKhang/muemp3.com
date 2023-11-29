package com.ja.muemp3.repositories;

import com.ja.muemp3.entities.Artist;
import com.ja.muemp3.entities.ArtistRole;
import com.ja.muemp3.payload.artist.ArtistResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistRoleRepository extends JpaRepository<ArtistRole, UUID> {
}
