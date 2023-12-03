package com.ja.muemp3.repositories;

import com.ja.muemp3.entities.ArtistType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistTypeRepository extends JpaRepository<ArtistType, UUID> {
}
