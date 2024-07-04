package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.enums.FileType;
import com.mue.payload.request.AlbumRequest;
import com.mue.payload.response.AlbumLineResponse;
import com.mue.payload.response.AlbumResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.payload.response.TrackLineResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.AlbumService;
import com.mue.services.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumApi {

    private final AlbumService albumService;
    private final TrackService trackService;

    @GetMapping
    public ResponseEntity<RestBody<?>> findAll(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query
    ) {

        List<ApiQuery> apiQueries = QueryRequest.from(query);
        System.out.println(apiQueries);
        InfiniteListResponse<AlbumLineResponse> albums = albumService.findAll(pageable, apiQueries);
        return ResponseEntity.ok(new RestBody<>(albums));
    }

    @GetMapping("{id}/tracks")
    public ResponseEntity<RestBody<?>> findTracks(
            @PathVariable UUID id) {
        List<TrackLineResponse> tracks = trackService.findByAlbumId(id);
        return ResponseEntity.ok(new RestBody<>(tracks));
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<RestBody<?>> create(@RequestBody @Valid AlbumRequest albumRequest, UriComponentsBuilder ucb) {
        UUID id = albumService.create(albumRequest);
        URI uri = ucb
                .path("/api/v1/albums/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Album created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<AlbumResponse>> findOne(@PathVariable UUID id){
        AlbumResponse details = albumService.findOne(id);
        return ResponseEntity.ok(new RestBody<>(details));
    }
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> softDelete(@PathVariable UUID id){
        albumService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> update(@PathVariable UUID id, @RequestBody @Valid AlbumRequest albumRequest){
        albumService.update(id, albumRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<RestBody<?>> searchByNameOrArtistOrTrack(Pageable pageable, @RequestParam("keyword") String keyword){
        InfiniteListResponse<AlbumLineResponse> albums = albumService.searchByNameOrArtistOrTrack(pageable, keyword);
        return ResponseEntity.ok(new RestBody<>(albums));
    }




}