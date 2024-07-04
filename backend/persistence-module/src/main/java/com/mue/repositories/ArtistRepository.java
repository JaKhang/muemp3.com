package com.mue.repositories;

import com.mue.entities.Artist;
import com.mue.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    Page<Artist> findAll(Specification<Artist> artistSpecification, Pageable pageable);

    @Query("select a from Artist a")
    List<Artist> findTop (Pageable sort);
}
