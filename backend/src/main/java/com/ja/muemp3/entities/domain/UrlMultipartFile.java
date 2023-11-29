package com.ja.muemp3.entities.domain;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;

public class UrlMultipartFile implements MultipartFile {

    private final Resource resource;

    public UrlMultipartFile(String url) {
        System.out.println(url);
        this.resource = UrlResource.from(url);
    }

    @Override
    public String getName() {
        try {
            return resource.getURI().getPath().concat("/").concat(Objects.requireNonNull(resource.getFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getOriginalFilename() {
        return resource.getFilename();
    }

    @Override
    public String getContentType() {
        return "image";
    }

    @Override
    public boolean isEmpty() {
        return !resource.exists();
    }

    @Override
    public long getSize() {
        try {
            return resource.contentLength();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public byte[] getBytes() throws IOException {
        return resource.getContentAsByteArray();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.getInputStream(), new FileOutputStream(dest));
    }

}
