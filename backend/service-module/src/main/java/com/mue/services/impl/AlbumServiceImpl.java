package com.mue.services.impl;

import com.mue.converters.Converter;
import com.mue.core.domain.ApiQuery;
import com.mue.core.exception.MethodNotImplementException;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Album;
import com.mue.entities.Artist;
import com.mue.entities.Genre;
import com.mue.entities.User;
import com.mue.payload.request.AlbumRequest;
import com.mue.payload.response.AlbumLineResponse;
import com.mue.payload.response.AlbumResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.repositories.AlbumRepository;
import com.mue.services.AlbumService;
import com.mue.specifications.SpecificationBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final Converter<AlbumLineResponse, AlbumResponse, Album, AlbumRequest> albumConverter;

    @Override
    public InfiniteListResponse<AlbumLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests) {
        Specification<Album> artistSpecification = SpecificationBuilder.build(queryRequests);
        Page<Album> albums = albumRepository.findAll(artistSpecification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(albumConverter::convertToLine).toList(),
                albums.hasNext()
        );
    }

    @Override
    public AlbumResponse findOne(UUID id) {
        Album album = findByIdElseThrow(id);
        return albumConverter.convertToDetails(album);
    }

    @Override
    public UUID create(AlbumRequest artistRequest) {
        Album album = albumConverter.convertToEntity(artistRequest);
        return albumRepository.save(album).getId();
    }

    @Override
    public void softDelete(UUID id) {
        Album album = findByIdElseThrow(id);
        album.setDeleted(true);
        albumRepository.save(album);
    }

    @Override
    public void update(UUID id, AlbumRequest artistRequest) {
        throw new MethodNotImplementException("Not implemented");
    }

    @Override
    public InfiniteListResponse<AlbumLineResponse> findAllByArtistId(UUID id, Pageable pageable) {
        Specification<Album> albumSpecification = (root, var, criteriaBuilder) -> {
            Join<Album, Artist> employees = root.join("artists");
            return criteriaBuilder.equal(employees.get("id"), id);
        };

        Page<Album> albums = albumRepository.findAll(albumSpecification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(albumConverter::convertToLine).toList(),
                albums.hasNext()
        );
    }

    @Override
    public InfiniteListResponse<AlbumLineResponse> findAllByUserId(UUID id, Pageable pageable) {
        Specification<Album> albumSpecification = (root, var, criteriaBuilder) -> {
            Join<Album, User> users = root.join("likedUsers");
            return criteriaBuilder.equal(users.get("id"), id);
        };

        Page<Album> albums = albumRepository.findAll(albumSpecification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(albumConverter::convertToLine).toList(),
                albums.hasNext()
        );
    }


    @Override
    public InfiniteListResponse<AlbumLineResponse> searchByNameOrArtistOrTrack(Pageable pageable, String keyword) {
        Specification<Album> albumSpecification = (root, var, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%");


        albumSpecification = albumSpecification.or((root, query, criteriaBuilder) -> {
            Join<Album, User> trackJoin = root.join("tracks");
            return criteriaBuilder.like(trackJoin.get("name"), "%" + keyword + "%");
        }).or((root, query, criteriaBuilder) -> {
            Join<Album, User> artistJoin = root.join("artists");
            return criteriaBuilder.like(artistJoin.get("name"), "%" + keyword + "%");
        });

        Page<Album> albums = albumRepository.findAll(albumSpecification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(albumConverter::convertToLine).toList(),
                albums.hasNext()
        );
    }

    @Override
    public InfiniteListResponse<AlbumLineResponse> findByGenreId(UUID id, Pageable pageable) {
        Specification<Album> specification = (root, var, criteriaBuilder) -> {
            Join<Genre, User> users = root.join("genres");
            return criteriaBuilder.equal(users.get("id"), id);
        };

        Page<Album> albums = albumRepository.findAll(specification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(albumConverter::convertToLine).toList(),
                albums.hasNext()
        );
    }


    private Album findByIdElseThrow(UUID id) {
        return albumRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Album", "ID", id)
        );
    }
}
