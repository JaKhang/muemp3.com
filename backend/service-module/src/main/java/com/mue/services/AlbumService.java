package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.payload.request.AlbumRequest;
import com.mue.payload.response.AlbumResponse;
import com.mue.payload.response.AlbumLineResponse;
import com.mue.payload.response.InfiniteListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AlbumService {
    InfiniteListResponse<AlbumLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests);
    AlbumResponse findOne(UUID id);

    UUID create(AlbumRequest artistRequest);
    void softDelete(UUID id);
    void update(UUID id, AlbumRequest artistRequest);
    InfiniteListResponse<AlbumLineResponse> findAllByArtistId(UUID id, Pageable pageable);

    InfiniteListResponse<AlbumLineResponse> findAllByUserId(UUID id, Pageable pageable);

    InfiniteListResponse<AlbumLineResponse> searchByNameOrArtistOrTrack(Pageable pageable, String keyword);


    InfiniteListResponse<AlbumLineResponse> findByGenreId(UUID id, Pageable pageable);
}
