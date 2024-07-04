package com.mue.api;

import com.mue.core.domain.ApiQuery;
import com.mue.core.domain.QueryRequest;
import com.mue.core.domain.RestBody;
import com.mue.enums.Bitrate;
import com.mue.enums.FileType;
import com.mue.payload.request.TrackRequest;
import com.mue.payload.response.TrackLineResponse;
import com.mue.payload.response.TrackResponse;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.TrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tracks")
@RequiredArgsConstructor
public class TrackApi {
    private final TrackService trackService;

    @GetMapping
    public ResponseEntity<RestBody<?>> findAll(
            Pageable pageable,
            @RequestParam(defaultValue = "") String query
    ) {

        List<ApiQuery> apiQueries = QueryRequest.from(query);
        System.out.println(query);
        InfiniteListResponse<TrackLineResponse> tracks = trackService.findAll(pageable, apiQueries);
        return ResponseEntity.ok(new RestBody<>(tracks));
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<RestBody<?>> create(@RequestBody @Valid TrackRequest trackRequest, UriComponentsBuilder ucb) {

        System.out.println(trackRequest);
        UUID id = trackService.create(trackRequest);
        URI uri = ucb
                .path("/api/v1/tracks/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(new RestBody<>("Track created", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<TrackResponse>> findOne(@PathVariable UUID id){
        TrackResponse details = trackService.findOne(id);
        return ResponseEntity.ok(new RestBody<>(details));
    }
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> softDelete(@PathVariable UUID id){
        trackService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<RestBody<Void>> update(@PathVariable UUID id, @RequestBody @Valid TrackRequest trackRequest){
        trackService.update(id, trackRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/streaming")
    public ResponseEntity<RestBody<Map<Bitrate, String>>> getStreamingUrl(@PathVariable UUID id){
        var url = trackService.getStreamUrl(id);
        return ResponseEntity.ok(new RestBody<>(url));
    }
    
}
