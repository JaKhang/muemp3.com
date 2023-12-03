package com.ja.muemp3.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface LocalFileService {
    Stream<Path> loadAll();

    Resource loadAsResource(String path);

    Path load(String subPath, String filename);

    String store(String subPath, MultipartFile file);

}
