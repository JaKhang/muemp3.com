package com.mue.repositories;

import com.mue.entities.ArtistType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArtistTypeRepository extends JpaRepository<ArtistType, UUID> {
}
