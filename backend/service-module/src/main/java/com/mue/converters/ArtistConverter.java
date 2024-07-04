package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.entities.*;
import com.mue.enums.FileType;
import com.mue.factories.URLFactory;
import com.mue.payload.request.ArtistRequest;
import com.mue.payload.response.ArtistLineResponse;
import com.mue.payload.response.ArtistResponse;
import com.mue.payload.response.ArtistTypeResponse;
import com.mue.payload.response.ImageResponse;
import com.mue.repositories.*;
import com.mue.security.domain.UserPrincipal;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ArtistConverter implements Converter<ArtistLineResponse, ArtistResponse, Artist, ArtistRequest> {
    private final URLFactory urlFactory;
    private final FileRepository fileRepository;
    private final CountryRepository countryRepository;
    private final ArtistTypeRepository artistTypeRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final LineConverter<ArtistTypeResponse, ArtistType> artistTypeMapper;
    private final DetailsConverter<ImageResponse, FileMetadata> imageMapper;

    @Override
    public ArtistLineResponse convertToLine(@Nullable Artist artist) {
        if (artist == null)
            return null;
        User user = getAuthenticatedUser();
        return ArtistLineResponse.builder()
                .id(artist.getId())
                .alias(artist.getAlias())
                .name(artist.getName())
                .thumbnail(urlFactory.generateThumbnailUrl(artist.getAvatar()))
                .liked(artist.isLike(user))
                .numberOfTracks(artist.countTracks())
                .likes(artist.countLikes())
                .build();
    }

    @Override
    public ArtistResponse convertToDetails(@Nullable Artist artist) {
        if (artist == null)
            return null;
        User user = getAuthenticatedUser();
        List<ArtistTypeResponse> types = artist.getTypes().stream().map(artistTypeMapper::convertToLine).toList();
        ImageResponse cover = imageMapper.convertToDetails(artist.getCoverImage());
        ImageResponse avatar = imageMapper.convertToDetails(artist.getAvatar());
        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .alias(artist.getAlias())
                .types(types)
                .typeNames(types.stream().map(ArtistTypeResponse::getName).toList())
                .birthday(artist.getBirthday())
                .lastModifiedAt(artist.getLastModifiedAt())
                .liked(artist.isLike(user))
                .description(artist.getDescription())
                .createdAt(artist.getCreatedAt())
                .avatarImage(avatar)
                .avatarUrl(avatar.getUrl())
                .coverImage(cover)
                .thumbnail(avatar.getThumbnail())
                .coverUrl(cover.getUrl())
                .likes(artist.countLikes())
                .build();
    }

    @Override
    public Artist convertToEntity(@Nonnull ArtistRequest artistRequest) {
        FileMetadata avatar = fileRepository.findByIdAndFileType(artistRequest.getAvatarId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "ID", artistRequest.getAvatarId()));
        FileMetadata cover = fileRepository.findByIdAndFileType(artistRequest.getCoverId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "ID", artistRequest.getCoverId()));
        Country country = countryRepository.findById(artistRequest.getCountryId()).orElseThrow(() -> new ResourceNotFoundException("Country", "ID", artistRequest.getCountryId()));
        List<ArtistType> artistTypes = artistTypeRepository.findAllById(artistRequest.getTypeIds());


        return Artist.builder()
                .alias(artistRequest.getAlias())
                .name(artistRequest.getName())
                .birthday(artistRequest.getBirthday())
                .isOfficial(artistRequest.getIsOfficial())
                .isBand(artistRequest.getIsBand())
                .isIndie(artistRequest.getIsIndie())
                .description(artistRequest.getDescription())
                .types(artistTypes)
                .deleted(false)
                .coverImage(cover)
                .country(country)
                .avatar(avatar)
                .build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
            return userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("Not found user with id " + principal.getId()));
        }
        return null;
    }


}
