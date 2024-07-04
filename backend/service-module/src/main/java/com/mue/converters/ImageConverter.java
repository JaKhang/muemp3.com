package com.mue.converters;

import com.mue.entities.FileMetadata;
import com.mue.factories.URLFactory;
import com.mue.payload.response.ImageResponse;
import com.mue.repositories.FileRepository;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ImageConverter implements DetailsConverter<ImageResponse, FileMetadata> {
    private final URLFactory urlFactory;
    private final FileRepository fileRepository;
    private static final UUID DEFAULT_IMAGE = UUID.fromString("ccd65457-a60f-4df8-ac5d-1d3064363773");

    @Override
    public ImageResponse convertToDetails(@Nullable FileMetadata fileMetadata) {
        if (fileMetadata == null)
            fileMetadata = fileRepository.findById(DEFAULT_IMAGE).orElse(null);

        if (fileMetadata == null)
            return ImageResponse.builder()
                    .id(null)
                    .thumbnail(null)
                    .url(null)
                    .build();
        return ImageResponse.builder()
                .id(fileMetadata.getId())
                .thumbnail(urlFactory.generateThumbnailUrl(fileMetadata))
                .url(urlFactory.generateUrl(fileMetadata))
                .build();
    }
}
