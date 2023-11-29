package com.ja.muemp3.services.storage;


import com.ja.muemp3.config.properties.StorageProperties;
import com.ja.muemp3.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LocalFileSystemService implements LocalFileService {
    /*------------------
      Config properties
    --------------------*/
    private final StorageProperties storageProperties;


    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Resource loadAsResource(String subPath, String filename) {
        try {
            Path file = load(subPath, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new ResourceNotFoundException("File", "File name", filename);

            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Path load(String subPath, String filename){
        return Path.of(storageProperties.getLocal().getRoot(), subPath, filename);
    }


    @Override
    public String store(String subPath, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            if(fileName.contains("..")){
                throw new RuntimeException();
            }


            Path destinationFile = Path.of("").resolve(storageProperties.getLocal().getRoot() + "/" +subPath).resolve(
                            Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            System.out.println(destinationFile.toUri());
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
        return subPath + "/" + fileName;
    }

}
