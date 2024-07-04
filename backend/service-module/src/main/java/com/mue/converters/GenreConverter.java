package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.FileMetadata;
import com.mue.entities.Genre;
import com.mue.enums.FileType;
import com.mue.payload.request.GenreRequest;
import com.mue.payload.response.GenreResponse;
import com.mue.payload.response.ImageResponse;
import com.mue.repositories.FileRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreConverter implements Converter<GenreResponse, GenreResponse, Genre,GenreRequest> {

    private final DetailsConverter<ImageResponse, FileMetadata> imageMapper;
    private final FileRepository fileRepository;

    @Override
    public GenreResponse convertToDetails(@Nullable Genre genre) {
        if(genre == null)
            return null;
        ImageResponse image = imageMapper.convertToDetails(genre.getCoverImage());
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .alias(genre.getAlias())
                .imageResponse(image)
                .color(genre.getColor())
                .build();
    }

    @Override
    public Genre convertToEntity(@Nonnull GenreRequest genreRequest) {
        FileMetadata image = fileRepository.findByIdAndFileType(genreRequest.getImageId(), FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "id", genreRequest.getImageId()));
        return Genre.builder()
                .alias(genreRequest.getAlias())
                .coverImage(image)
                .name(genreRequest.getName())
                .color(genreRequest.getColor())
                .deleted(false)
                .build();
    }

    @Override
    public GenreResponse convertToLine(@Nullable Genre genre) {
        return convertToDetails(genre);
    }
}
