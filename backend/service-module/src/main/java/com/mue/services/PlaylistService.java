package com.mue.services;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.entities.Track;
import com.mue.payload.request.GenreRequest;
import com.mue.payload.request.PlaylistRequest;
import com.mue.payload.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlaylistService {

    InfiniteListResponse<PlaylistResponse> findAll(List<ApiQuery> queryRequests, Pageable pageable);

    InfiniteListResponse<TrackLineResponse> findTrackByPlaylistId(UUID playlistId, Pageable pageable);

    PlaylistResponse findOne(UUID id);

    UUID create(PlaylistRequest genreRequest);

    void softDelete(UUID id);

    void update(UUID id, PlaylistRequest genreRequest);

    void addTracks(UUID playlistId, List<UUID> trackId);

    InfiniteListResponse<PlaylistResponse> findAllByUserId(UUID id, Pageable pageable);

    void removeTracks(UUID id, List<UUID> trackIds);

    InfiniteListResponse<PlaylistResponse> findAllByOwnerId(UUID ownerId, List<ApiQuery> queryRequests, Pageable pageable);

}
