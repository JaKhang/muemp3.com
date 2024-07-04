package com.mue.services.impl;

import com.mue.converters.DetailsConverter;
import com.mue.converters.EntityConverter;
import com.mue.converters.LineConverter;
import com.mue.core.domain.ApiQuery;
import com.mue.core.exception.MethodNotImplementException;
import com.mue.core.exception.PermissionDeniedException;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.UnauthorizedException;
import com.mue.entities.Album;
import com.mue.entities.PlayList;
import com.mue.entities.Track;
import com.mue.entities.User;
import com.mue.enums.FileType;
import com.mue.enums.Role;
import com.mue.payload.request.PlaylistRequest;
import com.mue.payload.response.InfiniteListResponse;
import com.mue.payload.response.PlaylistResponse;
import com.mue.payload.response.TrackLineResponse;
import com.mue.repositories.FileRepository;
import com.mue.repositories.PlaylistRepository;
import com.mue.repositories.TrackRepository;
import com.mue.services.PlaylistService;
import com.mue.services.UserService;
import com.mue.specifications.SpecificationBuilder;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final FileRepository fileRepository;
    private final DetailsConverter<PlaylistResponse, PlayList> playListConverter;
    private final EntityConverter<PlayList, PlaylistRequest> playListEntityConverter;
    private final LineConverter<TrackLineResponse, Track> trackConverter;
    private final TrackRepository trackRepository;
    private final UserService userService;

    @Override
    public InfiniteListResponse<PlaylistResponse> findAll(List<ApiQuery> queryRequests, Pageable pageable) {

        Specification<PlayList> artistSpecification = SpecificationBuilder.build(queryRequests);
        Page<PlayList> playlists = playlistRepository.findAll(artistSpecification, pageable);
        return new InfiniteListResponse<>(
                playlists.getNumberOfElements(),
                playlists.stream().map(playListConverter::convertToDetails).toList(),
                playlists.hasNext()
        );

    }

    @Override
    public InfiniteListResponse<TrackLineResponse> findTrackByPlaylistId(UUID playlistId, Pageable pageable) {
        PlayList playList = findByIdElseThrow(playlistId);
        if (playList.getIsPrivate()) {
            User user = userService.getAuthenticatedUser();
            if (!user.equals(playList.getCreatedBy()) && user.getRole() == Role.USER) {
                throw new UnauthorizedException("Playlist is private");
            }
        }
        Page<Track> tracks = trackRepository.findAllByPlaylistId(pageable, playList);
        return new InfiniteListResponse<>(
                tracks.getNumberOfElements(),
                tracks.stream().map(trackConverter::convertToLine).toList(),
                tracks.hasNext()
        );
    }

    @Override
    public PlaylistResponse findOne(UUID id) {
        PlayList playList = findByIdElseThrow(id);
        if (playList.getIsPrivate()) {
            User user = userService.getAuthenticatedUser();
            if (user == null || (!user.equals(playList.getCreatedBy()) && user.getRole() == Role.USER)) {
                throw new UnauthorizedException("Playlist is private");
            }
        }
        return playListConverter.convertToDetails(playList);

    }

    @Override
    public UUID create(PlaylistRequest playlistRequest) {
        PlayList playList = playListEntityConverter.convertToEntity(playlistRequest);
        return playlistRepository.save(playList).getId();

    }

    @Override
    public void softDelete(UUID id) {
        PlayList playList = findByIdElseThrow(id);
        if (checkPermission(playList)) {
            playList.setDeleted(true);
            playlistRepository.save(playList);
        } else
            throw new PermissionDeniedException("Permission denied");

    }

    @Override
    public void update(UUID id, PlaylistRequest playlistRequest) {
        User user = userService.getAuthenticatedUser();
        PlayList playList = findByIdElseThrow(id);
        if (!playList.getCreatedBy().equals(user))
            throw new AccessDeniedException("Only owner can modify !");

        playList.setAlias(playlistRequest.getAlias());
        playList.setName(playlistRequest.getName());
        playList.setIsPrivate(!playlistRequest.isPublic());
        playList.setDescription(playList.getDescription());
        playList.setCoverImage(
                fileRepository.findByIdAndFileType(playlistRequest.getImageId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "ID", playlistRequest.getImageId()))
        );
        playlistRepository.save(playList);
    }

    @Override
    public void addTracks(UUID playlistId, List<UUID> trackIds) {
        PlayList playList = findByIdElseThrow(playlistId);
        if (checkPermission(playList)) {
            List<Track> tracks = trackRepository.findAllById(trackIds);
            playList.getTracks().addAll(tracks);
            playlistRepository.save(playList);
        } else
            throw new PermissionDeniedException("Permission denied");


    }

    @Override
    public InfiniteListResponse<PlaylistResponse> findAllByUserId(UUID id, Pageable pageable) {
        Specification<PlayList> specification = (root, var, criteriaBuilder) -> {
            Join<PlayList, User> users = root.join("likedUsers");
            return criteriaBuilder.equal(users.get("id"), id);
        };

        Page<PlayList> albums = playlistRepository.findAll(specification, pageable);
        return new InfiniteListResponse<>(
                albums.getNumberOfElements(),
                albums.stream().map(playListConverter::convertToDetails).toList(),
                albums.hasNext()
        );
    }

    @Override
    public void removeTracks(UUID id, List<UUID> trackIds) {
        PlayList playList = findByIdElseThrow(id);
        if (!checkPermission(playList)) {
            throw new PermissionDeniedException("Permission denied");
        }
        List<Track> tracks = trackRepository.findAllById(trackIds);
        tracks.forEach(playList.getTracks()::remove);
        playlistRepository.save(playList);
    }


    private PlayList findByIdElseThrow(UUID id) {
        return playlistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Playlist", "ID", id));
    }

    private boolean checkPermission(PlayList playList) {
        User user = userService.getAuthenticatedUser();
        if (user.getRole() == Role.ADMIN)
            return true;
        else return !playList.getIsPrivate() || user.equals(playList.getCreatedBy());
    }

    @Override
    public InfiniteListResponse<PlaylistResponse> findAllByOwnerId(UUID ownerId, List<ApiQuery> queryRequests, Pageable pageable) {
        Specification<PlayList> artistSpecification = SpecificationBuilder.build(queryRequests);
        Specification<PlayList> ownerSpec = (root, var, builder) -> {
            Join<PlayList, User> users = root.join("createdBy");
            return builder.equal(users.get("id"), ownerId);
        };

        Page<PlayList> playlists = playlistRepository.findAll(artistSpecification
                .and(ownerSpec), pageable);
        return new InfiniteListResponse<>(
                playlists.getNumberOfElements(),
                playlists.stream().map(playListConverter::convertToDetails).toList(),
                playlists.hasNext()
        );
    }
}
