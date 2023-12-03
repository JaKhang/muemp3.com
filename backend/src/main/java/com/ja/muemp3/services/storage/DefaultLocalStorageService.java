package com.ja.muemp3.services.storage;

import com.ja.muemp3.config.properties.StorageProperties;
import lombok.NonNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class DefaultLocalStorageService implements LocalStorageService {
    private final Path root;
    public DefaultLocalStorageService(StorageProperties storageProperties) {
        if (Strings.isBlank(storageProperties.getLocal().getRoot())) {
            throw new StorageException("File upload location can not be Empty.");
        } else {
            this.root = Paths.get(storageProperties.getLocal().getRoot());
        }
    }


    @Override
    public void init() {

    }

    @Override
    public String store(@NonNull MultipartFile file, String path) {



        try {
            //check empty
            if (file.isEmpty()) throw new StorageException("File upload location can not be Empty.");
            Path desFile = root.toAbsolutePath()
                    .resolve(path)
                    .resolve(Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();

            System.out.println(desFile.toAbsolutePath());
            System.out.println(root.toAbsolutePath().resolve("path"));
            // This is a security check
            if (!desFile.startsWith(root))
                throw new StorageException("Cannot store file outside current directory.");

            if(!desFile.getParent().toFile().exists()){
                desFile.toFile().mkdirs();
            }
            //store
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, desFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return desFile.toAbsolutePath().toString();

        } catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public boolean createDirectory(String path, String dirname) {
        try {
            Path dirPath = root.resolve(path).resolve(dirname).normalize().toAbsolutePath();
            Files.createDirectory(dirPath);
            return true;
        } catch (IOException e){
            throw new StorageException("Failed to create directory", e);
        }
    }

    @Override
    public Resource load(String path) {
        try {
            Path file = loadPath(path);
            Resource resource = new UrlResource(file.toUri());
            System.out.println(resource);
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new StorageFileNotFoundException("Could not read file: " + path);
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }

    @Override
    public boolean deleted(String path) {
        try {
            Path filePath = loadPath(path);
            if (filePath.toFile().isDirectory()) {
                FileSystemUtils.deleteRecursively(filePath);
            } else {
                Files.deleteIfExists(filePath);
            }
            return true;
        } catch (IOException e) {
            throw new StorageException("Could not deleted dir");
        }

    }

    @Override
    public Stream<Path> listFile(String path) throws IOException {
        try {
            Path dirPath = loadPath(path);
            return Files.list(dirPath);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Resource loadAsResource(String resource) {
        return load(resource);
    }

    /*------------------
          Private
    --------------------*/
    private Path loadPath(String... paths) {
        Path path = root.resolve(Paths.get("", paths)).normalize().toAbsolutePath();

        return path;
    }
}
