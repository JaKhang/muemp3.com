package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.entities.*;
import com.mue.enums.FileType;
import com.mue.factories.URLFactory;
import com.mue.payload.request.AlbumRequest;
import com.mue.payload.response.*;
import com.mue.repositories.*;
import com.mue.security.domain.UserPrincipal;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AlbumConverter implements Converter<AlbumLineResponse, AlbumResponse, Album, AlbumRequest> {

    private final DetailsConverter<ImageResponse, FileMetadata> imageMapper;
    private final LineConverter<ArtistLineResponse, Artist> artistMapper;
    private final LineConverter<TrackLineResponse, Track> trackMapper;
    private final FileRepository fileRepository;
    private final ArtistRepository artistRepository;
    private final GenreRepository genreRepository;
    private final URLFactory factory;
    private final ObjectConverter objectConverter;
    private final TrackRepository trackRepository;
    private final DistributorRepository distributorRepository;
    private final UserRepository userRepository;

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal principal) {
            return userRepository.findById(principal.getId()).orElseThrow(() -> new UserNotFoundException("Not found user with id " + principal.getId()));
        }
        return null;
    }

    @Override
    public AlbumResponse convertToDetails(@Nullable Album album) {
        if (album == null)
            return null;
        ImageResponse image = imageMapper
                .convertToDetails(album.getCoverImage());
        List<TrackLineResponse> tracks = album
                .getTracks()
                .stream()
                .map(trackMapper::convertToLine)
                .toList();
        List<ArtistLineResponse> artists = album
                .getArtists()
                .stream()
                .map(artistMapper::convertToLine).toList();

        return AlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .type(album.getType())
                .alias(album.getAlias())
                .createdAt(album.getCreatedAt())
                .thumbnail(image.getThumbnail())
                .description(album.getDescription())
                .lastModifiedAt(album.getLastModifiedAt())
                .shortDescription(album.getShortDescription())
                .numberOfTrack(album.getNumberOfTracks())
                .liked(album.isLiked(getAuthenticatedUser()))
                .lastModifiedAt(album.getLastModifiedAt())
                .createdAt(album.getCreatedAt())
                .duration(album.getDuration())
                .artists(artists)
                .tracks(tracks)
                .image(image)
                .build();
    }

    @Override
    public Album convertToEntity(@Nonnull AlbumRequest albumRequest) {
        FileMetadata image = fileRepository.findByIdAndFileType(albumRequest.getImageId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "Id", albumRequest.getImageId()));
        List<Artist> artists = artistRepository.findAllById(albumRequest.getArtistIds());
        List<Genre> genres = genreRepository.findAllById(albumRequest.getGenreIds());
        List<Track> tracks = trackRepository.findAllById(albumRequest.getTrackIds());
        Distributor distributor = distributorRepository.findById(albumRequest.getDistributorId()).orElseThrow(() -> new ResourceNotFoundException("Distributor", "Id", albumRequest.getImageId()));

        return Album.builder()
                .distributor(distributor)
                .coverImage(image)
                .artists(artists)
                .genres(genres)
                .deleted(false)
                .name(albumRequest.getName())
                .tracks(tracks)
                .releaseDate(albumRequest.getReleaseDate())
                .shortDescription(albumRequest.getShortDescription())
                .description(albumRequest.getDescription())
                .alias(albumRequest.getAlias())
                .type(albumRequest.getType())
                .isIndie(albumRequest.isIndie())
                .build();
    }

    @Override
    public AlbumLineResponse convertToLine(@Nullable Album album) {
        if (album == null)
            return null;
        //Mapping to simplified objects
        List<ObjectResponse> artists = album
                .getArtists()
                .stream()
                .map(objectConverter::convert).toList();
        List<ObjectResponse> genres = album
                .getGenres()
                .stream()
                .map(objectConverter::convert)
                .toList();
        return AlbumLineResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .type(album.getType())
                .alias(album.getAlias())
                .liked(album.isLiked(getAuthenticatedUser()))
                .duration(album.getDuration())
                .releaseDate(album.getReleaseDate())
                .numberOfTrack(album.getNumberOfTracks())
                .shortDescription(album.getShortDescription())
                .thumbnail(factory.generateThumbnailUrl(album.getCoverImage()))
                .artists(artists)
                .genres(genres)
                .build();
    }
}
