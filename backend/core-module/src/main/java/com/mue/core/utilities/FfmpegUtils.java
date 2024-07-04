package com.mue.core.utilities;

import java.io.IOException;

public class FfmpegUtils {

    public static void resizeImage(String inputImagePath, String outputImagePath, int targetWidth, int targetHeight){
        try {
            // Execute FFmpeg command to resize the imageId
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("ffmpeg", "-i", inputImagePath, "-vf", "scale=" + targetWidth + ":" + targetHeight, "-y", outputImagePath);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Failed to resize the imageId");
            }
            System.out.println("Image resized successfully.");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to resize the imageId", e);
        }
    }
}
