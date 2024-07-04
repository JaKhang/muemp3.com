package com.mue.api;

import com.mue.core.domain.RestBody;
import com.mue.payload.request.GenreRequest;
import com.mue.payload.response.AlbumLineResponse;
import com.mue.payload.response.GenreResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.AlbumService;
import com.mue.services.GenreService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreApi {
    private final GenreService artistService;
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<RestBody<?>> findAll() {


        List<GenreResponse> artists = artistService.findAll();
        return ResponseEntity.ok(new RestBody<>(artists));
    }
    @GetMapping("{id}/albums")
    public ResponseEntity<RestBody<?>> findAlbums(@PathVariable UUID id, Pageable pageable) {

        InfiniteListResponse<AlbumLineResponse> albums = albumService.findByGenreId(id, pageable);
        return ResponseEntity.ok(new RestBody<>(albums));
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<RestBody<UUID>> create(
            @RequestBody @Valid GenreRequest artistRequest,
            UriComponentsBuilder ucb
    ) {
        UUID id = artistService.create(artistRequest);
        URI uri = ucb
                .path("/api/v1/artists/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Genre created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<GenreResponse>> findOne(@PathVariable UUID id) {
        GenreResponse details = artistService.findOne(id);
        return ResponseEntity.ok(new RestBody<>(details));
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> softDelete(@PathVariable UUID id) {
        artistService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> update(
            @PathVariable UUID id,
            @RequestBody @Valid GenreRequest artistRequest
    ) {
        artistService.update(id, artistRequest);
        return ResponseEntity.noContent().build();
    }


}

