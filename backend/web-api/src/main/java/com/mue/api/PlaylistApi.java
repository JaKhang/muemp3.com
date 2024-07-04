package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.ErrorResponse;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.payload.request.ActionRequest;
import com.mue.payload.request.PlaylistRequest;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.payload.response.PlaylistResponse;
import com.mue.payload.response.TrackLineResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.security.annotaton.IsAuthenticated;
import com.mue.services.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
@Tag(name = "Playlist API", description = "")
public class PlaylistApi {

    private final PlaylistService playlistService;


    @Operation(
            summary = "Find infinite list of playlists",
            description = "Find infinite list of playlists with paging and query",
            tags = {"playlists", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Invalid ID ", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<RestBody<InfiniteListResponse<PlaylistResponse>>> findAll(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query
    ) {

        List<ApiQuery> apiQueries = QueryRequest.from(query);
        InfiniteListResponse<PlaylistResponse> playlists = playlistService.findAll(apiQueries, pageable);
        return ResponseEntity.ok(new RestBody<>(playlists));
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<RestBody<?>> findAllTrackOfPlaylist(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query,
            @PathVariable UUID id) {

        InfiniteListResponse<TrackLineResponse> playlists = playlistService.findTrackByPlaylistId(id, pageable);
        return ResponseEntity.ok(new RestBody<>(playlists));
    }

    @PostMapping("/{id}/tracks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RestBody<?>> addTracks(@PathVariable UUID id,@RequestBody ActionRequest actionRequest) {
        playlistService.addTracks(id, actionRequest.getIds());
        return ResponseEntity.ok(new RestBody<>("Add success", null));
    }

    @DeleteMapping("/{id}/tracks")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RestBody<?>> removeTracks(@PathVariable UUID id, @RequestBody ActionRequest actionRequest) {
        playlistService.removeTracks(id, actionRequest.getIds());
        return ResponseEntity.ok(new RestBody<>("Remove success", null));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RestBody<?>> create(@RequestBody @Valid PlaylistRequest playlistRequest, UriComponentsBuilder ucb) {
        UUID id = playlistService.create(playlistRequest);
        URI uri = ucb
                .path("/api/v1/playlists/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Playlist created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<PlaylistResponse>> findOne(@PathVariable UUID id) {
        PlaylistResponse details = playlistService.findOne(id);
        return ResponseEntity.ok(new RestBody<>(details));
    }

    @DeleteMapping("/{id}")
    @IsAuthenticated
    public ResponseEntity<RestBody<Void>> softDelete(@PathVariable UUID id) {
        playlistService.softDelete(id);
        return ResponseEntity.ok(new RestBody<>("Delete success", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestBody<Void>> update(@PathVariable UUID id, @RequestBody @Valid PlaylistRequest playlistRequest) {
        playlistService.update(id, playlistRequest);
        return ResponseEntity.noContent().build();
    }
}
