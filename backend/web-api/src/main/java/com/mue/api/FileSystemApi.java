package com.mue.api;


import com.mue.core.domain.RestBody;
import com.mue.enums.FileType;
import com.mue.payload.request.FolderRequest;
import com.mue.payload.response.ExplorerResponse;
import com.mue.payload.response.FileResponse;
import com.mue.payload.response.TrashResponse;
import com.mue.security.annotaton.IsAdmin;
import com.mue.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


@RequestMapping("/api/v1/files")
@RestController
@RequiredArgsConstructor
@IsAdmin
public class FileSystemApi {

    private final FileService fileService;



    @PostMapping(value = "/folders/{folderId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<RestBody<UUID>> upload(MultipartFile file, @PathVariable UUID folderId) {
        UUID fileId = fileService.upload(file, folderId);
        URI uri = UriComponentsBuilder.newInstance().build(fileId).normalize();
        RestBody<UUID> body = new RestBody<>("File has been uploaded", fileId);
        return ResponseEntity.created(uri).body(body);
    }

    @PostMapping("/folders")
    public ResponseEntity<RestBody<UUID>> createdFolder(@RequestBody FolderRequest folder) {
        UUID fileId = fileService.createFolder(folder.getParentId(), folder.getName());
        URI uri = UriComponentsBuilder.newInstance().build(fileId).normalize();
        RestBody<UUID> body = new RestBody<>("Folder has been created", fileId);
        return ResponseEntity.created(uri).body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestBody<?>> deletedFile(@PathVariable UUID id) {
        fileService.deletedFile(id);
        return ResponseEntity.ok(new RestBody<>("File has been deleted", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestBody<FileResponse>> findById(
            @PathVariable UUID id) {
        FileResponse fileRes = fileService.findById(id);
        return ResponseEntity.ok(new RestBody<>("", fileRes));
    }


    @GetMapping("/folders/{id}")
    public ResponseEntity<RestBody<ExplorerResponse>> findFolderById(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "18") Integer limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "createdAt") String property,
            @RequestParam(required = false) FileType fileType,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "false") boolean deleted
    ) {
        Sort sort = Sort.by(direction, property);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        ExplorerResponse explorerResponse = fileService.findFolderChildren(pageable, id, fileType, name, deleted);
        return ResponseEntity.ok(new RestBody<>(explorerResponse));
    }

    @GetMapping(value = "/folders/root")
    public ResponseEntity<RestBody<ExplorerResponse>> findRootFolder(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "18") Integer limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "createdAt") String property,
            @RequestParam(required = false) FileType fileType,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "false") Boolean deleted
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit, direction, property);
        ExplorerResponse folderRes = fileService.findRootFolder(pageable, fileType, name, deleted);
        return ResponseEntity.ok(new RestBody<>(folderRes));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> handleDownload(@PathVariable UUID id) {
        Resource file = fileService.load(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/trash")
    public ResponseEntity<RestBody<TrashResponse>> findFilesInTrash(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "18") Integer limit,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "createdAt") String property,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) FileType fileType
    ) {

        Pageable pageable = PageRequest.of(page - 1, limit, direction, property);
        TrashResponse folderRes = fileService.findAllInTrash(pageable, fileType, name);
        return ResponseEntity.ok(new RestBody<>(folderRes));
    }


    @PutMapping("/move/{id}")
    public ResponseEntity<RestBody<?>> moveTo(@PathVariable UUID id, @RequestBody FolderRequest folderRequest) {
        System.out.println(folderRequest);
        fileService.updateFileSystem(id, folderRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/move-to-trash/{id}")
    public ResponseEntity<RestBody<UUID>> moveToTrash(@PathVariable UUID id) {
        fileService.moveToTrash(id);
        return ResponseEntity.ok().body(new RestBody<>("File already has been moved to trash", id));
    }

    @PutMapping("/restore-from-trash/{id}")
    public ResponseEntity<RestBody<ExplorerResponse>> restoreFromTrash(@PathVariable UUID id) {
        fileService.restoreFromTrash(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clear-trash")
    public ResponseEntity<RestBody<ExplorerResponse>> clearTrash() {
        fileService.clearTrash();
        return ResponseEntity.noContent().build();
    }



}
