package com.mue.converters;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.Distributor;
import com.mue.entities.FileMetadata;
import com.mue.enums.FileType;
import com.mue.payload.request.DistributorRequest;
import com.mue.payload.response.DistributorResponse;
import com.mue.payload.response.ImageResponse;
import com.mue.repositories.FileRepository;
import com.mue.services.UserService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DistributorConverter implements Converter<DistributorResponse, DistributorResponse, Distributor, DistributorRequest> {

    private final FileRepository fileRepository;
    private final DetailsConverter<ImageResponse, FileMetadata> imageMapper;

    @Override
    public DistributorResponse convertToDetails(@Nullable Distributor distributor) {
        if (distributor == null)
            return null;
        ImageResponse image = imageMapper.convertToDetails(
                distributor.getCoverImage()
        );

        return DistributorResponse.builder()
                .id(distributor.getId())
                .name(distributor.getName())
                .alias(distributor.getAlias())
                .image(image)
                .thumbnail(image.getThumbnail())
                .build();
    }

    @Override
    public Distributor convertToEntity(@Nonnull DistributorRequest distributorRequest) {
        FileMetadata image = findImageById(distributorRequest.getCoverId());

        return Distributor.builder()
                .name(distributorRequest.getName())
                .alias(distributorRequest.getAlias())
                .coverImage(image)
                .build();
    }

    @Override
    public DistributorResponse convertToLine(@Nullable Distributor distributor) {
        if (distributor == null)
            return null;
        return convertToDetails(distributor);
    }

    private FileMetadata findImageById(UUID id) {
        return fileRepository.findByIdAndFileType(id, FileType.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Image", "ID", id));
    }
}
