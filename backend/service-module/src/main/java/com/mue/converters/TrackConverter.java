package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.UserNotFoundException;
import com.mue.entities.*;
import com.mue.enums.FileType;
import com.mue.factories.URLFactory;
import com.mue.payload.request.TrackRequest;
import com.mue.payload.response.ArtistLineResponse;
import com.mue.payload.response.ImageResponse;
import com.mue.payload.response.TrackResponse;
import com.mue.payload.response.TrackLineResponse;
import com.mue.repositories.*;
import com.mue.security.domain.UserPrincipal;
import com.mue.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackConverter implements Converter<TrackLineResponse, TrackResponse, Track, TrackRequest> {
    private final DistributorRepository distributorRepository;
    private final ArtistRepository artistRepository;
    private final ObjectConverter objectConverter;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final DetailsConverter<ImageResponse, FileMetadata> imageConverter;
    private final LineConverter<ArtistLineResponse, Artist> artistConverter;
    private final URLFactory urlFactory;
    private final FileMapper fileMapper;
    private final UserService userService;

    @Override
    public TrackResponse convertToDetails(Track track) {
        var artists = track.getArtists().stream().map(artistConverter::convertToLine).toList();
        var genres = track.getGenres().stream().map(objectConverter::convert).toList();
        var album = objectConverter.convert(track.getAlbum());
        var user = userService.getAuthenticatedUser();
        var distributor = objectConverter.convert(track.getDistributor());
        var image = imageConverter.convertToDetails(track.getCoverImage());
        var audio = fileMapper.map(track.getAudio());
        var composer = objectConverter.convert(track.getComposer());
        return TrackResponse.builder()
                .id(track.getId())
                .name(track.getName())
                .alias(track.getAlias())
                .genres(genres)
                .artists(artists)
                .album(album)
                .isLike(track.isLike(user))
                .createdAt(track.getCreatedAt())
                .lastModifiedAt(track.getLastModifiedAt())
                .isIndie(track.getIsIndie())
                .duration(track.getDuration())
                .mvLink(track.getMvLink())
                .lyrics(track.getLyrics())
                .releaseDate(track.getReleaseDate())
                .index(track.getTrackIndex())
                .description(track.getDescription())
                .image(image)
                .distributor(distributor)
                .composer(composer)
                .audio(audio)
                .isOfficial(track.getIsOfficial())
                .build();
    }

    @Override
    public Track convertToEntity(TrackRequest trackRequest) {
        Album album = trackRequest.getAlbumId() == null ? null : albumRepository.findById(trackRequest.getAlbumId()).orElseThrow(() -> new ResourceNotFoundException("Album", "id", trackRequest.getAlbumId()));
        Distributor distributor = trackRequest.getDistributorId() == null ? null : distributorRepository.findById(trackRequest.getDistributorId()).orElseThrow(() -> new ResourceNotFoundException("Distributor", "id", trackRequest.getDistributorId()));
        FileMetadata image = trackRequest.getImageId() == null ? null : fileRepository.findByIdAndFileType(trackRequest.getImageId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "Id", trackRequest.getImageId()));
        FileMetadata audio = trackRequest.getAudioId() == null ? null : fileRepository.findByIdAndFileType(trackRequest.getAudioId(), FileType.AUDIO).orElseThrow(() -> new ResourceNotFoundException("Audio", "Id", trackRequest.getAudioId()));
        Artist composer = trackRequest.getComposerId() == null ? null : artistRepository.findById(trackRequest.getComposerId()).orElseThrow(()-> new ResourceNotFoundException("Artist", "Id", trackRequest.getComposerId()));
        List<Genre> genres = genreRepository.findAllById(trackRequest.getGenreIds());
        List<Artist> artists = artistRepository.findAllById(trackRequest.getArtistIds());

        return Track.builder()
                .name(trackRequest.getName())
                .alias(trackRequest.getAlias())
                .description(trackRequest.getDescription())
                .duration(trackRequest.getDuration())
                .isIndie(trackRequest.getIsIndie())
                .trackIndex(trackRequest.getTrackIndex())
                .lyrics(trackRequest.getLyrics())
                .mvLink(trackRequest.getMvLink())
                .status(trackRequest.getStatus())
                .isOfficial(trackRequest.getIsOfficial())
                .releaseDate(trackRequest.getReleaseDate())
                .composer(composer)
                .distributor(distributor)
                .coverImage(image)
                .artists(artists)
                .genres(genres)
                .album(album)
                .audio(audio)
                .build();
    }

    @Override
    public TrackLineResponse convertToLine(Track track) {
        var artists = track.getArtists().stream().map(objectConverter::convert).toList();
        var genres = track.getGenres().stream().map(objectConverter::convert).toList();
        var album = objectConverter.convert(track.getAlbum());
        var user = userService.getAuthenticatedUser();
        return TrackLineResponse.builder()
                .id(track.getId())
                .alias(track.getAlias())
                .name(track.getName())
                .image(imageConverter.convertToDetails(track.getCoverImage()))
                .genres(genres)
                .artists(artists)
                .album(album)
                .duration(track.getDuration())
                .thumbnail(urlFactory.generateThumbnailUrl(track.getCoverImage()))
                .liked(track.isLike(user))
                .build();
    }

}
