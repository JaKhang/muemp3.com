package com.ja.muemp3.services.v1;

import com.ja.muemp3.entities.Artist;
import com.ja.muemp3.entities.ArtistType;
import com.ja.muemp3.entities.Image;
import com.ja.muemp3.entities.constants.StorageType;
import com.ja.muemp3.entities.constants.ThumbnailSize;
import com.ja.muemp3.exception.ResourceNotFoundException;
import com.ja.muemp3.factories.LinkFactory;
import com.ja.muemp3.payload.artist.ArtistRequest;
import com.ja.muemp3.payload.artist.ArtistResponse;
import com.ja.muemp3.payload.artist.ArtistTypeResponse;
import com.ja.muemp3.repositories.ArtistRepository;
import com.ja.muemp3.repositories.ArtistTypeRepository;
import com.ja.muemp3.services.ArtistService;
import com.ja.muemp3.services.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
    private final ArtistTypeRepository artistTypeRepository;

    private static final String IMAGE_DIR = "artist";

    @Override
    public List<ArtistResponse> findAll() {
        return artistRepository.findAll().stream().map(this::convertToLine).toList();
    }

    @Override
    public ArtistResponse save(ArtistRequest artistRequest) {
        System.out.println(artistRequest);
        Artist artist = convertToEntity(artistRequest);
        artist = artistRepository.saveAndFlush(artist);
        return convertToLine(artist);
    }

    @Override
    @Transactional
    public ArtistResponse saveWithImage(ArtistRequest artistRequest, MultipartFile thumbnail) {
        UUID imageId = imageService.uploadImage(thumbnail, StorageType.LOCAL, IMAGE_DIR);
        artistRequest.setThumbnailId(imageId);
        return save(artistRequest);
    }

    @Override
    public ArtistResponse update(UUID id, ArtistRequest r) {
        Artist artist = findByIdElseThrow(id);

        Image thumbnail = imageService.findByIdElseNull(r.getThumbnailId());
        List<ArtistType> artistTypes = artistTypeRepository.findAllById(r.getTypeIds());

        artist.setName(r.getName());
        artist.setAlias(r.getAlias());
        artist.setBirthday(r.getBirthday());
        artist.setDescription(r.getDescription());
        artist.setIsIndie(r.getIsIndie());
        artist.setIsOfficial(r.getIsOfficial());
        artist.setThumbnail(thumbnail);
        artist.setTypes(artistTypes);
        artist = artistRepository.save(artist);

        return convertToLine(artist);
    }

    @Override
    public ArtistResponse updateThumbnail(UUID id, MultipartFile thumbnail) {
        UUID thumbId = imageService.uploadImage(thumbnail, null, IMAGE_DIR);
        Image image = imageService.findByIdElseNull(id);
        Artist artist = findByIdElseThrow(id);
        artist.setThumbnail(image);
        artist = artistRepository.save(artist);
        return convertToLine(artist);
    }

    @Override
    public ArtistResponse delete(UUID id) {
        Artist artist = findByIdElseThrow(id);
        artist.setDeleted(true);
        return convertToLine(artist);
    }

    @Override
    public List<ArtistTypeResponse> findAllType() {
        return artistTypeRepository.findAll().stream().map(artistType -> mapper.map(artistType, ArtistTypeResponse.class)).toList();
    }


    /*------------------
          Private
    --------------------*/
    private ArtistResponse convertToLine(Artist artist){
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

    private Artist convertToEntity(ArtistRequest artistRequest){
        Image thumbnail = imageService.findByIdElseNull(artistRequest.getThumbnailId());
        var types = artistTypeRepository.findAllById(artistRequest.getTypeIds());
        return Artist
                .builder()
                .types(types)
                .alias(artistRequest.getAlias())
                .isIndie(artistRequest.getIsIndie())
                .birthday(artistRequest.getBirthday())
                .name(artistRequest.getName())
                .description(artistRequest.getDescription())
                .thumbnail(thumbnail)
                .isOfficial(artistRequest.getIsOfficial())
                .build();
    }

    private Artist findByIdElseThrow(UUID id){
        return artistRepository.findById(id).orElseThrow(
                () ->new ResourceNotFoundException("Artist","ID", id)
        );
    }
}
