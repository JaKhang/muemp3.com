package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.payload.request.DistributorRequest;
import com.mue.payload.response.DistributorResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.DistributorService;
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
@RequestMapping("/api/v1/distributors")
public class DistributorApi {
    private final DistributorService artistService;

    @GetMapping
    public ResponseEntity<RestBody<?>> findAll(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query
    ) {

        List<ApiQuery> apiQueries = QueryRequest.from(query);
        InfiniteListResponse<DistributorResponse> artists = artistService.findAll(pageable, apiQueries);
        return ResponseEntity.ok(new RestBody<>(artists));
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<RestBody<UUID>> create(
            @RequestBody @Valid DistributorRequest artistRequest,
            UriComponentsBuilder ucb
    ) {
        UUID id = artistService.create(artistRequest);
        URI uri = ucb
                .path("/api/v1/artists/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Distributor created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<DistributorResponse>> findOne(@PathVariable UUID id) {
        DistributorResponse details = artistService.findOne(id);
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
            @RequestBody @Valid DistributorRequest artistRequest
    ) {
        artistService.update(id, artistRequest);
        return ResponseEntity.noContent().build();
    }

}
