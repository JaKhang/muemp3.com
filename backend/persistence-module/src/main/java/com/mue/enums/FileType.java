package com.mue.enums;



import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;
import java.util.List;

public enum FileType {
    VIDEO("mp4"),
    IMAGE("jpg", "jpeg", "png", "webp"),
    AUDIO("mp3"),
    CODE,
    DOCUMENT("docx"),
    ORDER;



    public static FileType of(String filename) {
        String ext = FilenameUtils.getExtension(filename);
        return Arrays.stream(values()).filter(type -> type.is(ext)).findFirst().orElseThrow(() -> new RuntimeException(""));

    }

    private final List<String> extensions;

    FileType(String... extensions) {
        this.extensions = List.of(extensions);
    }

    public boolean is(String extension){
        return this.extensions.contains(extension.toLowerCase());
    }


}
