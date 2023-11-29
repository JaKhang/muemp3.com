package com.ja.muemp3.repositories;

import com.ja.muemp3.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.stream.Stream;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
   Stream<Artist> findAllByDeleted(Boolean deleted);

}
