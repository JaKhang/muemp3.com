package com.mue.services.impl;


import com.mue.core.config.StorageProperties;
import com.mue.core.exception.FileAlreadyExistsException;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.StorageException;
import com.mue.core.exception.StorageFileNotFoundException;
import com.mue.services.BlobService;
import com.mue.core.utilities.FfmpegUtils;
import lombok.Getter;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileBlobService implements BlobService {

    @Getter
    private final Path root;
    private final Path trash;
    private final Path thumbnailDir;

    private final static String SEPARATOR = File.separator;

    private final int thumbnailSize;

    public FileBlobService(StorageProperties storageProperties) {
        root = Path.of(storageProperties.getLocal().getRootDir());
        trash = Path.of(storageProperties.getLocal().getTrashDir());
        thumbnailDir = Path.of(storageProperties.getLocal().getThumbnailDir());
        thumbnailSize = storageProperties.getThumbnailSize();

        if (!root.toFile().exists())
            root.toFile().mkdirs();
        if (!trash.toFile().exists())
            trash.toFile().mkdirs();
        if (!thumbnailDir.toFile().exists())
            thumbnailDir.toFile().mkdirs();

    }

    @Override
    public Path loadAbsolutePath(String relativePath) {
        return root.resolve(relativePath).normalize();
    }

    @Override
    public void store(MultipartFile file, String fileName) {
        if (file.isEmpty()) throw new StorageException("File upload path can not be Empty.");
        Path relativePath = Path.of(fileName).normalize();
        try {
            Path AbsolutePath = root.resolve(relativePath).normalize();
            if (!AbsolutePath.startsWith(root))
                throw new StorageException("Cannot store file outside current directory.");

            if (!AbsolutePath.getParent().toFile().exists()) {
                Files.createDirectory(AbsolutePath);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, AbsolutePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void delete(String des) {
        Path desFile = loadPath(des);
        try {
            FileSystemUtils.deleteRecursively(desFile);
        } catch (IOException e) {
            throw new StorageException();
        }
    }

    @Override
    public String createFolder(String path, String folderName) {
        Path relativePath = Path.of(path, folderName).normalize();
        try {
            Path dirPath = root.resolve(relativePath);
            if (dirPath.toFile().exists())
                throw new FileAlreadyExistsException("Folder already exists");
            Files.createDirectory(dirPath);
            return relativePath.toString();
        } catch (IOException e) {
            throw new StorageException("Failed to create directory", e);
        }

    }

    @Override
    @Transactional
    public String move(String src, String dest) {
        Path srcFile = root.resolve(src).normalize();
        Path desFile = root.resolve(dest).normalize();
        if (!desFile.startsWith(root))
            throw new StorageException("Cannot store file outside current directory.");

        try {
            if (!desFile.getParent().toFile().exists()) {
                Files.createDirectory(desFile.getParent());
            }
            Files.move(srcFile, desFile, StandardCopyOption.ATOMIC_MOVE);
            return dest;
        } catch (IOException e) {
            throw new StorageException();
        }
    }

    @Override
    public void moveToTrash(String filePath) {
        Path src = root.resolve(filePath).normalize();
        if (!src.toFile().exists())
            throw new ResourceNotFoundException("File blob", "path", filePath);
        Path des= trash.resolve(filePath).normalize();
        try {
            des.toFile().mkdirs();
            Files.move(src, des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearTrash() {
        try {
            FileSystemUtils.deleteRecursively(trash);
            Files.createDirectory(trash);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void createThumbnail(String file) {
        Path filePath = root.resolve(file);
        if (!filePath.toFile().exists()){
            throw new StorageException("File not exists");
        }
        if (!filePath.toFile().isFile()){
            throw new RuntimeException("Can not create thumbnail for dir");
        }
        Path srcPath = root.resolve(file);
        Path destPath = thumbnailDir.resolve(FilenameUtils.getBaseName(file).concat(".jpg"));
        FfmpegUtils.resizeImage(srcPath.toString(), destPath.toString() , thumbnailSize, thumbnailSize);
    }


    @Override
    public void deletedThumbnail(String file){
        Path destPath = thumbnailDir.resolve(FilenameUtils.getBaseName(file).concat(".jpg"));
        delete(destPath.toString());
    }
    public Path loadPath(String path) {
        return root.resolve(path)
                .normalize()
                .toAbsolutePath();
    }
    @Override
    public void resizeImage(String src, String dest, int width, int height) {
        try {
            Path srcPath = loadPath(src);
            Path destPath = loadPath(dest);
            Thumbnails.of(srcPath.toFile())
                    .size(width, height)
                    .outputFormat("JPEG")
                    .outputQuality(1)
                    .toFile(destPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restoreFromTrash(String path) {
        Path src = trash.resolve(path).normalize();
        if (!src.toFile().exists())
            throw new ResourceNotFoundException("File blob", "path", path);
        Path des = root.resolve(path);
        if (!des.getParent().toFile().exists() && !des.startsWith(root)){
            des.toFile().mkdirs();
        }
        try {
            Files.move(src, des, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Restore form trash failed");
        }
    }


    public static void main(String[] args) {
        Path root = Path.of("home/media");
        Path location = root.resolve("image").normalize();

        System.out.println();
    }
}
