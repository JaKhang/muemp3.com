package com.mue.fileprocessor.api.v1;

import com.mue.core.domain.RestBody;
import com.mue.services.AudioProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audio-process")
@RequiredArgsConstructor
public class AudioProcessApi {
    private final AudioProcessService audioProcessService;


    @PatchMapping("/hls/{id}")
    public ResponseEntity<RestBody<?>> processHls(@PathVariable UUID id) {
        var urls = audioProcessService.processToM3u8(id);
        return ResponseEntity.status(HttpStatus.OK).body(new RestBody<>("Audio already has been processed ", urls));
    }

    public static void main(String[] args) {
        String[] cmd = {"ffmpeg", "-i", "D:\\Workspace\\full-stack\\muemp3.com\\storage\\cffa5963-c61e-45d7-bebb-62e2e7edd0f9.mp3", "-c:a", "aac", "-b:a", "320k", "-hls_time", "10", "-f", "hls", "-hls_playlist_type", "vod", "-hls_list_size", "0", "-hls_segment_filename", "D:\\Workspace\\full-stack\\muemp3.com\\storage\\hls\\cffa5963-c61e-45d7-bebb-62e2e7edd0f9\\320k\\index-%02d.aac", "D:\\Workspace\\full-stack\\muemp3.com\\storage\\hls\\cffa5963-c61e-45d7-bebb-62e2e7edd0f9\\320k\\index.m3u8"};


        try {
//            Process process = new ProcessBuilder()
//                    .command(
//                            cmd
//                    )
//                    .start();
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new RuntimeException("");
//            }
            Runtime.getRuntime().exec(cmd).waitFor();
            System.out.println("Image resized successfully.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
