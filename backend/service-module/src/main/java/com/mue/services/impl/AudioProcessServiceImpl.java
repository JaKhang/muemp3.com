package com.mue.services.impl;

import com.mue.core.config.HLSProperties;
import com.mue.core.exception.ResourceNotFoundException;
import com.mue.entities.FileMetadata;
import com.mue.enums.Bitrate;
import com.mue.enums.FileType;
import com.mue.factories.URLFactory;
import com.mue.repositories.FileRepository;
import com.mue.services.AudioProcessService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AudioProcessServiceImpl implements AudioProcessService {


    private final FileRepository fileRepository;
    private final FileBlobService fileBlobService;
    private final HLSProperties hlsProperties;
    private final URLFactory urlFactory;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<Bitrate, String> processToM3u8(UUID ID) {
        FileMetadata audioFile = fileRepository.findByIdAndFileType(ID, FileType.AUDIO).orElseThrow(() -> new ResourceNotFoundException("Audio FIle", "ID", ID));
        Path src = fileBlobService.loadAbsolutePath(audioFile.getPath());
        for (var bitrate : Bitrate.values()) {
            Path path = loadRoot(
                    audioFile.getId().toString(),
                    bitrate.toString()
            );
            path.toFile().mkdirs();
            logger.info("Created dir " + path.toString());
            processToM3u8(src, path, bitrate.toString());
        }

        return urlFactory.generateHlsUrl(audioFile);


    }

    @Override
    public void processToM3u8(Path src, Path path, String bitrate) {
        logger.info("Start process " + bitrate);
        int hlsTime = hlsProperties.getTime();
        String segmentFileName = hlsProperties.getSegmentFileName();
        String manifest = hlsProperties.getFileName();
        String[] cmd = {"ffmpeg", "-i", src.toString(), "-c:a", "aac", "-b:a", bitrate, "-hls_time", hlsTime + "", "-f", "hls", "-hls_playlist_type", "vod", "-hls_list_size", "0", "-hls_segment_filename",  path.resolve(segmentFileName).toString(), path.resolve(manifest).toString()};

        try {


            Process process = new ProcessBuilder()
                    .command(cmd)
                    .directory(path.toFile())
                    .start();


            new Thread(() -> {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        logger.info(line);
                    }
                } catch (IOException ignored) {

                }
            }).start();

            new Thread(() -> {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        logger.info(line);
                    }
                } catch (IOException ignored) {

                }
            }).start();
            if (process.waitFor() != 0) {
                throw new RuntimeException("");
            }
            logger.info("Already process {}", bitrate);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processToM3u8V2(Path src, Path path, String bitrate) {

    }


    private Path loadRoot(String... paths) {
        return Path.of(hlsProperties.getRoot(), paths);
    }

}
