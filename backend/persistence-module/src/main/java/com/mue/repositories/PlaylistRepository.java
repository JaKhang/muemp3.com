package com.mue.repositories;

import com.mue.entities.Album;
import com.mue.entities.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<PlayList, UUID> {

    Page<PlayList> findAll(Specification<PlayList> artistSpecification, Pageable pageable);
}
