package com.ja.muemp3.api.v1;

import com.ja.muemp3.annotations.JsonArg;
import com.ja.muemp3.services.storage.GoogleDriveFile;
import com.ja.muemp3.payload.artist.ArtistResponse;
import com.ja.muemp3.payload.artist.ArtistRequest;
import com.ja.muemp3.payload.response.ApiResponse;
import com.ja.muemp3.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistApi {

    private final ArtistService artistService;

    private final GoogleDriveFile googleDriveFile;

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> findAll() throws GeneralSecurityException, IOException {
        List<ArtistResponse> data = artistService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(data));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(ArtistRequest info){
        ArtistResponse artistResponse = artistService.save(info);
        return created(artistResponse);
    }

    @PostMapping(value = "/include-image")
    public ResponseEntity<ApiResponse<?>> createWithImage(@RequestPart("info") ArtistRequest artistRequest, @RequestPart("thumbnail") MultipartFile thumbnail){
        ArtistResponse artistResponse = artistService.saveWithImage(artistRequest, thumbnail);
        return created(artistResponse);
    }

    @PostMapping("/include-image-link")
    public ResponseEntity<ApiResponse<?>> createWithImageLink(@JsonArg("link") String link, @JsonArg("info") ArtistRequest info){
        System.out.println(link);
        System.out.println(info);
        ArtistResponse artistResponse = artistService.saveWithImageLink(info, link);
        return created(artistResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> update(ArtistRequest info, @PathVariable UUID id){
        ArtistResponse artistResponse = artistService.update(id, info);
        return ResponseEntity.ok(ApiResponse.ok(artistResponse));
    }


    @PutMapping("/{id}/thumbnail")
    public ResponseEntity<?> updateThumbnail(@PathVariable UUID id, MultipartFile thumbnail){
        ArtistResponse artistResponse = artistService.updateThumbnail(id, thumbnail);
        return ResponseEntity.ok(ApiResponse.ok(artistResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, MultipartFile thumbnail){
        ArtistResponse artistResponse = artistService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(artistResponse));
    }

    /*------------------

    --------------------*/
    private ResponseEntity<ApiResponse<?>> created(ArtistResponse artistResponse){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(artistResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(ApiResponse.created(artistResponse));
    }

}
