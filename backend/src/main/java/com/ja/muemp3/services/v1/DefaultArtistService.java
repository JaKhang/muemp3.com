package com.ja.muemp3.services.v1;

import com.ja.muemp3.entities.Artist;
import com.ja.muemp3.entities.ArtistRole;
import com.ja.muemp3.entities.Image;
import com.ja.muemp3.entities.constants.StorageType;
import com.ja.muemp3.entities.constants.ThumbnailSize;
import com.ja.muemp3.factories.LinkFactory;
import com.ja.muemp3.payload.artist.ArtistRequest;
import com.ja.muemp3.payload.artist.ArtistResponse;
import com.ja.muemp3.repositories.ArtistRepository;
import com.ja.muemp3.repositories.ArtistRoleRepository;
import com.ja.muemp3.services.ArtistService;
import com.ja.muemp3.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultArtistService implements ArtistService {

    private final ImageService imageService;
    private final ModelMapper mapper;
    private final ArtistRepository artistRepository;
    private final LinkFactory linkFactory;
    private final ArtistRoleRepository artistRoleRepository;

    @Override
    public List<ArtistResponse> findAll() {
        return artistRepository.findAll().stream().map(this::convert).toList();
    }

    @Override
    public ArtistResponse save(ArtistRequest artistRequest) {
        Image thumbnail = imageService.findByIdElseNull(artistRequest.getThumbnailId());
        var roles = artistRoleRepository.findAllById(artistRequest.getRoleIds());
        Artist artist = Artist
                .builder()
                .roles(roles)
                .alias(artistRequest.getAlias())
                .isBand(artistRequest.getIsBand())
                .birthday(artistRequest.getBirthday())
                .name(artistRequest.getName())
                .description(artistRequest.getDescription())
                .thumbnail(thumbnail)
                .isOfficial(artistRequest.getIsOfficial())
                .build();


        artist = artistRepository.saveAndFlush(artist);

        return convert(artist);
    }

    @Override
    public ArtistResponse saveWithImage(ArtistRequest artistRequest, MultipartFile thumbnail) {
        UUID imageId = imageService.uploadImage(thumbnail, StorageType.GOOGLE_DRIVE, "/artist");
        artistRequest.setThumbnailId(imageId);
        return save(artistRequest);
    }

    @Override
    public ArtistResponse saveWithImageLink(ArtistRequest artistRequest, String link) {
        UUID imageId = imageService.uploadImageWithLink(link, null, "/artist");
        artistRequest.setThumbnailId(imageId);
        return save(artistRequest);
    }

    @Override
    public ArtistResponse update(UUID id, ArtistRequest artistRequest) {
        return null;
    }

    @Override
    public ArtistResponse updateThumbnail(UUID id, MultipartFile thumbnail) {
        return null;
    }

    @Override
    public ArtistResponse delete(UUID id) {
        return null;
    }

    /*------------------
          Private
    --------------------*/
    private ArtistResponse convert(Artist artist){
        return ArtistResponse.builder()
                .id(artist.getId())
                .alias(artist.getAlias())
                .name(artist.getName())
                .createdAt(artist.getCreatedAt().getTime())
                .lastModifiedAt(artist.getLastModifiedAt().getTime())
                .thumbnail(linkFactory.createThumbnailLink(artist.getThumbnail(), ThumbnailSize.SMALL))
                .thumbnailMD(linkFactory.createThumbnailLink(artist.getThumbnail(), ThumbnailSize.MEDIUM))
                .thumbnailLG(linkFactory.createThumbnailLink(artist.getThumbnail(), ThumbnailSize.LARGE))
                .build();
    }
}
