package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.payload.request.ArtistRequest;
import com.mue.payload.response.ArtistResponse;
import com.mue.payload.response.ArtistLineResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.payload.response.ObjectResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface ArtistService {

    InfiniteListResponse<ArtistLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests);

    ArtistResponse findOne(UUID id);

    UUID create(ArtistRequest artistRequest);

    void softDelete(UUID id);

    void update(UUID id, ArtistRequest artistRequest);

    List<ObjectResponse> findAllType();

    InfiniteListResponse<ArtistLineResponse> findAllByUserId(UUID id, Pageable pageable);

    List<ArtistLineResponse> findTop(int top, Sort sort);
}
