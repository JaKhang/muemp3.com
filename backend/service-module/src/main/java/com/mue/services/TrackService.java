package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.enums.Bitrate;
import com.mue.payload.request.TrackRequest;
import com.mue.payload.response.TrackLineResponse;
import com.mue.payload.response.TrackResponse;
import com.mue.payload.response.InfiniteListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TrackService {
    InfiniteListResponse<TrackLineResponse> findAll(Pageable pageable, List<ApiQuery> queryRequests);
    TrackResponse findOne(UUID id);
    UUID create(TrackRequest trackRequest);
    void softDelete(UUID id);
    void update(UUID id, TrackRequest trackRequest);

    Map<Bitrate, String> getStreamUrl(UUID id);

    InfiniteListResponse<TrackLineResponse> findAllByUserId(UUID id, Pageable pageable);

    List<TrackLineResponse> findByAlbumId(UUID id);
}
