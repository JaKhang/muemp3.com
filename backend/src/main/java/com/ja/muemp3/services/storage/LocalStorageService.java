package com.ja.muemp3.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface LocalStorageService{
    void init();
    String store(MultipartFile file, String path);
    boolean createDirectory(String path, String dirname);
    Resource load(String path);
    boolean deleted(String path);
    Stream<Path> listFile(String path) throws IOException;
}
