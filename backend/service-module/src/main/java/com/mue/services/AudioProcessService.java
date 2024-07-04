package com.mue.services;

import com.mue.enums.Bitrate;

import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

public interface AudioProcessService {
    Map<Bitrate, String> processToM3u8(UUID ID);


    void processToM3u8(Path src, Path path, String bitrate);
    void processToM3u8V2(Path src, Path path, String bitrate);
}
