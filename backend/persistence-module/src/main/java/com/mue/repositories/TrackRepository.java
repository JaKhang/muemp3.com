package com.mue.repositories;

import com.mue.entities.PlayList;
import com.mue.entities.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {
    Page<Track> findAll(Specification<Track> trackSpecification, Pageable pageable);

    @Query("SELECT t FROM Track t WHERE :playlist MEMBER OF t.playLists")
    Page<Track> findAllByPlaylistId(Pageable pageable, PlayList playlist);

    List<Track> findAllByAlbumId(UUID id, Sort trackIndex);
}
