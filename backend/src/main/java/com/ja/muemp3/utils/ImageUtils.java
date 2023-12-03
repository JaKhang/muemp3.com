package com.ja.muemp3.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] resizeImage(byte[] bytes, int targetWidth, int targetHeight) throws IOException {

        Image resultingImage = ImageIO.read(new ByteArrayInputStream(bytes));
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", outStream);
        return outStream.toByteArray();
    }
}
