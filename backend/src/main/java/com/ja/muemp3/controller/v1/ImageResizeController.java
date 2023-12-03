package com.ja.muemp3.controller.v1;

import com.ja.muemp3.entities.Image;
import com.ja.muemp3.services.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ImageResizeController {

    private final ImageService imageService;

    @GetMapping(value = "/images/{id}", produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> display(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "", name = "s") Integer size,
            @RequestParam(required = false, name = "w") Integer width,
            @RequestParam(required = false, name = "h") Integer height
    ) throws IOException {


        if (size != null){
            width = size;
            height = size;
        }

        Resource resource = imageService.loadResourceById(id);
        if (width != null && height != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(resource.getFile())
                    .size(width, height)
                    .outputFormat("JPEG")
                    .toOutputStream(outputStream);
            resource = new ByteArrayResource(outputStream.toByteArray());
        }


        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(resource.contentLength())
                .body(resource);

    }

}
