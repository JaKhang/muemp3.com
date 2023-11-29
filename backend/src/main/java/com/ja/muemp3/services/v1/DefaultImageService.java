package com.ja.muemp3.services.v1;

import com.ja.muemp3.entities.Image;
import com.ja.muemp3.entities.constants.StorageType;
import com.ja.muemp3.entities.domain.UrlMultipartFile;
import com.ja.muemp3.exception.BadRequestException;
import com.ja.muemp3.repositories.ImageRepository;
import com.ja.muemp3.services.ImageService;
import com.ja.muemp3.services.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DefaultImageService implements ImageService {

    private final StorageService storageService;
    private final ImageRepository imageRepository;
    @Override
    public Image findByIdElseNull(UUID thumbnailLId) {
        return imageRepository.findById(thumbnailLId).orElse(null);
    }

    @Override
    public UUID uploadImage(MultipartFile multipartFile, StorageType storageType, String path) {
        String resource = storageService.upload(storageType, multipartFile, path, true);
        String link = storageService.getLinkByResource(resource, storageType);
        Image image = Image.builder()
                .size(multipartFile.getSize())
                .name(multipartFile.getOriginalFilename())
                .storageType(storageType)
                .resource(resource)
                .link(link)
                .build();
        image = imageRepository.saveAndFlush(image);
        return image.getId();
    }

    @Override
    public UUID uploadImageWithLink(String link, StorageType storageType, String s) {
        try {
            MultipartFile multipartFile = new UrlMultipartFile(link);
            return uploadImage(multipartFile, storageType, s);
        } catch (Exception e){
            throw new BadRequestException("");
        }
    }

}
