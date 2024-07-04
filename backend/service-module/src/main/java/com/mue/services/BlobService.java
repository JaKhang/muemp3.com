package com.mue.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * this interface define storage file blob
 * @author JaKhang
 */
public interface BlobService {


    Path loadAbsolutePath(String relativePath);

    /**
     * This method store file blog to directory
     * @param file File upload
     * @param filename Relative path of the directory that will contain the file
     * @see String
     * @see MultipartFile
     */
    void store(MultipartFile file, String filename);



    /**
     * delete file or directory
     *
     * @param filePath filePath
     */
    void delete(String filePath);



    /**
     * create folder
     *
     * @param parentDir Relative path of the directory that will contain the file
     * @param folderName name of folder
     * @return {@link String} Relative path of created folder
     * @see String
     */
    String createFolder(String parentDir, String folderName);



    /**
     * move file to new location
     *
     * @param src source file
     * @param dest dest file
     * @return new relative path
     */
    String move(String src, String dest);

    void moveToTrash(String src);

    void clearTrash();

    Resource load(String path);

    void createThumbnail(String file);

    void deletedThumbnail(String file);

    void resizeImage(String src, String dest, int width, int height);

    void restoreFromTrash(String path);
}
