package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.payload.request.GenreRequest;
import com.mue.payload.response.GenreResponse;
import com.mue.payload.response.GenreResponse;
import com.mue.payload.response.InfiniteListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    List<GenreResponse> findAll();

    GenreResponse findOne(UUID id);

    UUID create(GenreRequest genreRequest);

    void softDelete(UUID id);

    void update(UUID id, GenreRequest genreRequest);
}
