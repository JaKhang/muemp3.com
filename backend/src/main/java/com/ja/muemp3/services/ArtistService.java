package com.ja.muemp3.services;

import com.ja.muemp3.payload.artist.ArtistResponse;
import com.ja.muemp3.payload.artist.ArtistRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ArtistService {
    List<ArtistResponse> findAll();

    ArtistResponse save(ArtistRequest artistRequest);

    ArtistResponse saveWithImage(ArtistRequest artistRequest, MultipartFile thumbnail);

    ArtistResponse saveWithImageLink(ArtistRequest artistRequest, String link);

    ArtistResponse update(UUID id, ArtistRequest artistRequest);

    ArtistResponse updateThumbnail(UUID id, MultipartFile thumbnail);

    ArtistResponse delete(UUID id);
}
