package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.payload.request.AlbumRequest;
import com.mue.payload.request.ArtistRequest;
import com.mue.payload.response.*;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.AlbumService;
import com.mue.services.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistApi {

    private final ArtistService artistService;
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<RestBody<?>> findAll(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query
    ) {

        List<ApiQuery> apiQueries = QueryRequest.from(query);
        System.out.println(query);
        InfiniteListResponse<ArtistLineResponse> artists = artistService.findAll(pageable, apiQueries);
        return ResponseEntity.ok(new RestBody<>(artists));
    }

    @GetMapping("/top")
    public ResponseEntity<RestBody<?>> findAll(
            Sort sort,
            @RequestParam(defaultValue = "10") int top
    ) {

        List<ArtistLineResponse> artists = artistService.findTop(top, sort);
        return ResponseEntity.ok(new RestBody<>(artists));
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<RestBody<?>> create(@RequestBody @Valid ArtistRequest artistRequest, UriComponentsBuilder ucb) {
        UUID id = artistService.create(artistRequest);
        URI uri = ucb
                .path("/api/v1/artists/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Artist created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<ArtistResponse>> findOne(@PathVariable UUID id) {
        ArtistResponse details = artistService.findOne(id);
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
    public ResponseEntity<RestBody<Void>> update(@PathVariable UUID id, @RequestBody @Valid ArtistRequest artistRequest) {
        artistService.update(id, artistRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/types")
    public ResponseEntity<RestBody<List<ObjectResponse>>> findAllType() {
        var types = artistService.findAllType();
        return ResponseEntity.ok(new RestBody<>(types));
    }


    @GetMapping("/{id}/albums")
    public ResponseEntity<RestBody<InfiniteListResponse<?>>> update(@PathVariable UUID id, Pageable pageable) {
        InfiniteListResponse<AlbumLineResponse> albums = albumService.findAllByArtistId(id, pageable);
        return ResponseEntity.ok(new RestBody<>(albums));
    }
}
