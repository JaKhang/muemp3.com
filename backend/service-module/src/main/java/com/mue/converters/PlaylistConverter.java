package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.FileMetadata;
import com.mue.entities.PlayList;
import com.mue.entities.User;
import com.mue.enums.FileType;
import com.mue.payload.request.PlaylistRequest;
import com.mue.payload.response.ImageResponse;
import com.mue.payload.response.PlaylistResponse;
import com.mue.repositories.FileRepository;
import com.mue.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaylistConverter implements DetailsConverter<PlaylistResponse, PlayList>, EntityConverter<PlayList, PlaylistRequest> {
    private final DetailsConverter<ImageResponse, FileMetadata> imageConverter;
    private final FileRepository fileRepository;
    private final UserService userService;

    @Override
    public PlaylistResponse convertToDetails(PlayList playList) {
        ImageResponse image = imageConverter.convertToDetails(playList.getCoverImage());
        User user = userService.getAuthenticatedUser();
        return PlaylistResponse.builder()
                .id(playList.getId())
                .name(playList.getName())
                .alias(playList.getAlias())
                .image(image)
                .thumbnail(image.getThumbnail())
                .likes(playList.getLikes())
                .owner(playList.getCreatedBy().getName())
                .totalTrack(playList.getNumberOfTrack())
                .liked(playList.isLiked(user))
                .build();
    }

    @Override
    public PlayList convertToEntity(PlaylistRequest playlistRequest) {
        User user = userService.getAuthenticatedUser();

        FileMetadata image = null;
        if (playlistRequest.getImageId() != null)
            image = fileRepository.findByIdAndFileType(playlistRequest.getImageId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "ID", playlistRequest.getImageId()));
        return PlayList.builder()
                .name(playlistRequest.getName())
                .description(playlistRequest.getDescription())
                .createdBy(user)
                .alias(playlistRequest.getAlias())
                .isPrivate(!playlistRequest.isPublic())
                .isOfficial(playlistRequest.isOfficial())
                .coverImage(image)
                .build();
    }
}
