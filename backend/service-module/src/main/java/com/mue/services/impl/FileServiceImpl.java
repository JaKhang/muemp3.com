package com.mue.services.impl;

import com.mue.core.exception.ResourceNotFoundException;
import com.mue.core.exception.StorageException;
import com.mue.entities.FileMetadata;
import com.mue.entities.FileSystemMetadata;
import com.mue.entities.FolderMetadata;
import com.mue.enums.FileType;
import com.mue.converters.FileMapper;
import com.mue.payload.request.FolderRequest;
import com.mue.payload.response.ExplorerResponse;
import com.mue.payload.response.FileResponse;
import com.mue.payload.response.FolderResponse;
import com.mue.payload.response.TrashResponse;
import com.mue.repositories.FileRepository;
import com.mue.repositories.FileSystemRepository;
import com.mue.repositories.FolderRepository;
import com.mue.services.BlobService;
import com.mue.services.FileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.mue.enums.FileType.IMAGE;
import static com.mue.specifications.FileSystemSpecifications.*;
import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR_STR;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileSystemRepository fileSystemRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final BlobService blobService;
    private final FileMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    @Transactional
    public UUID upload(MultipartFile file, UUID folderId) {
        //find parent
        FolderMetadata parent = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder", "ID", folderId));
        FileType fileType = FileType.of(Objects.requireNonNull(file.getOriginalFilename()));

        String metadataName = generateFileSystemName(file.getOriginalFilename(), parent);

        //create entity
        FileMetadata fileMetadata = FileMetadata.builder()
                .name(metadataName)
                .alt(file.getOriginalFilename())
                .size(file.getSize())
                .mimetype(file.getContentType())
                .parent(parent)
                .isFolder(false)
                .fileType(fileType)
                .build();
        //save new file metadata
        fileRepository.save(fileMetadata);

        //rename file to <id>.<extension>
        String filename = fileMetadata.getId().toString()
                .concat(EXTENSION_SEPARATOR_STR)
                .concat(FilenameUtils.getExtension(file.getOriginalFilename()));

        //save blob file
        blobService.store(file, filename);

        //changePath
        fileMetadata.setPath(filename);
        fileRepository.save(fileMetadata);


        if (fileType == IMAGE)
            blobService.createThumbnail(fileMetadata.getPath());

        return fileMetadata.getId();
    }

    private String generateFileSystemName(String name, FolderMetadata folderMetadata) {
        String extension = FilenameUtils.getExtension(name);
        String basename = FilenameUtils.getBaseName(name);
        int counter = 1;
        while (fileSystemRepository.existsByNameAndParent(name, folderMetadata)) {
            if (!extension.isBlank()) {
                name = basename.concat("-" + counter).concat(EXTENSION_SEPARATOR_STR).concat(extension);
            } else {
                name = basename.concat("-" + counter);
            }
            counter++;
        }

        return name;
    }

    @Override
    public Resource load(UUID fileId) {
        FileMetadata fileMetadata = findFileByIdElseThrow(fileId);
        return blobService.load(fileMetadata.getPath());
    }

    @Override
    @Transactional
    public void moveToTrash(UUID id) {
        FileSystemMetadata fileSystem = fileSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File system", "id", id));
        fileSystem.setInTrash(true);
        moveToTrashRecursively(fileSystem);
    }

    @Override
    @Transactional
    public void restoreFromTrash(UUID id) {
        FileSystemMetadata fileSystem = fileSystemRepository.findInTrashById(id).orElseThrow(() -> new ResourceNotFoundException("File system", "id", id));
        FolderMetadata parent = fileSystem.getParent();
        fileSystem.setInTrash(false);
        if (parent == null || parent.isDeleted()) {
            fileSystem.setParent(
                    findRoot()
            );
        }
        restoreFromTrashRecursively(fileSystem);
    }

    @Override
    public void updateFileSystem(UUID id, FolderRequest request) {
        FileSystemMetadata fileSystem = fileSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File system", "id", id));
        FolderMetadata folderMetadata = findFolderByIdElseThrow(request.getParentId());
        fileSystem.setParent(folderMetadata);
        if (!request.getName().isEmpty()) {
            fileSystem.setName(request.getName());
        }
        fileSystemRepository.save(fileSystem);

    }

    private void moveToTrashRecursively(FileSystemMetadata fileSystem) {
        if (fileSystem.isDeleted()){
            return;
        }
        fileSystem.setDeleted(true);
        fileSystemRepository.save(fileSystem);
        if (fileSystem instanceof FolderMetadata folder) {
            folder.getChildren().forEach(this::moveToTrashRecursively);
        } else if (fileSystem instanceof FileMetadata file){
            blobService.moveToTrash(file.getPath());
        }
    }

    private void restoreFromTrashRecursively(FileSystemMetadata fileSystem) {
        if (!fileSystem.isDeleted()){
            return;
        }
        fileSystem.setDeleted(false);
        fileSystemRepository.save(fileSystem);
        if (fileSystem instanceof FolderMetadata folderMetadata) {
            folderMetadata.getChildren().forEach(this::restoreFromTrashRecursively);
        } else if (fileSystem instanceof FileMetadata file){
            blobService.restoreFromTrash(file.getPath());
        }
    }


    @Override
    public UUID makeACopy(UUID fileID) {
        return null;
    }

    @Override
    @Transactional
    public UUID createFolder(UUID parentId, String name) {
        FolderMetadata parent = findFolderByIdElseThrow(parentId);
        name = generateFileSystemName(name, parent);
        FolderMetadata folder = FolderMetadata.builder()
                .name(name)
                .parent(parent)
                .isFolder(true)
                .build();
        return folderRepository.save(folder).getId();
    }

    @Override
    public ExplorerResponse findFolderChildren(Pageable pageable, UUID folderId, FileType type, String name, boolean deleted) {
        //file parent
        FolderMetadata folder = findFolderByIdElseThrow(folderId);
        System.out.println(folder);

        //Group folder and file
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("isFolder"));
        orders.addAll(pageable.getSort().get().toList());
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(orders)
        );
        List<FolderResponse> parents = findParents(folderId);

        //query file
        var fileSystems = fileSystemRepository.findAll(
                where(parentEquals(folder)).and(typeIs(type).or((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isFolder"))).and(deletedIs(false)).and(nameLike(name)))
                , pageable);

        //map to DTO
        var files = fileSystems.map(fileSystem -> {
            if (fileSystem instanceof FileMetadata fileMetadata) {
                return mapper.map(fileMetadata);
            }
            return mapper.map((FolderMetadata) fileSystem);
        }).getContent();

        return ExplorerResponse.builder()
                .files(files)
                .current(mapper.map(folder))
                .hasMore(fileSystems.hasNext())
                .parents(parents)
                .build();
    }

    @Override
    @Transactional
    public void deletedFile(UUID id) {
        FileSystemMetadata fileSystem = findFileSystemByIdElseThrow(id);
        if (fileSystem.getParent() == null)
            throw new StorageException("Can't delete root folder");
        deleteRecursively(fileSystem);
    }

    private void deleteRecursively(FileSystemMetadata fileSystemMetadata) {
        if (fileSystemMetadata instanceof FileMetadata fileMetadata) {
            blobService.delete(fileMetadata.getPath());
            if (IMAGE.is(fileMetadata.getName())) {
                blobService.deletedThumbnail(fileMetadata.getPath());
            }
            fileRepository.delete(fileMetadata);
        } else if (fileSystemMetadata instanceof FolderMetadata folderMetadata) {
            for (var child : folderMetadata.getChildren()) {
                deleteRecursively(child);
            }
            folderRepository.delete(folderMetadata);
        }
    }

    @Override
    public FileResponse findById(UUID id) {
        return null;
    }

    @Override
    public ExplorerResponse findRootFolder(Pageable pageable, FileType fileType, String queryName, boolean deleted) {
        FolderMetadata folder = findRoot();
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("isFolder"));
        orders.addAll(pageable.getSort().get().toList());
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(orders)
        );
        List<FolderResponse> parents = findParents(folder.getId());
        var fileSystems = fileSystemRepository.findAll(
                where(parentEquals(folder)).and(typeIs(fileType).or((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isFolder"))).and(deletedIs(false)).and(nameLike(queryName)))
                , pageable);
        ;
        var files = fileSystems.map(fileSystem -> {
            if (fileSystem instanceof FileMetadata fileMetadata) {
                return mapper.map(fileMetadata);
            }
            return mapper.map((FolderMetadata) fileSystem);
        }).getContent();

        return ExplorerResponse.builder()
                .files(files)
                .current(mapper.map(folder))
                .hasMore(fileSystems.hasNext())
                .parents(parents)
                .build();
    }

    @Override
    public void clearTrash() {
        List<FileSystemMetadata> InTrashFileSystems = findFileSystemInTrash();
        InTrashFileSystems.forEach(this::deleteRecursively);
    }

    @Override
    public TrashResponse findAllInTrash(Pageable pageable, FileType fileType, String name) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("isFolder"));
        orders.addAll(pageable.getSort().get().toList());
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(orders)
        );
        var fileSystems = fileSystemRepository.findAll(
                where((typeIs(fileType)).or((root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isFolder"))).and(inTrash()).and(nameLike(name)))
                , pageable);
        ;
        var files = fileSystems.map(fileSystem -> {
            if (fileSystem instanceof FileMetadata fileMetadata) {
                return mapper.map(fileMetadata);
            }
            return mapper.map((FolderMetadata) fileSystem);
        }).getContent();

        return TrashResponse.builder()
                .files(files)
                .hasMore(fileSystems.hasNext())
                .build();
    }

    private List<FileSystemMetadata> findFileSystemInTrash(){
        return fileSystemRepository.findAll((root, query, builder) -> builder.isTrue(root.get("inTrash")));
    }


    /*------------------
          Private
    --------------------*/
    FolderMetadata findFolderByIdElseThrow(UUID id) {
        return folderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Folder", "ID", id));
    }

    FileSystemMetadata findFileSystemByIdElseThrow(UUID id) {
        return fileSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File system", "ID", id));
    }

    private FileMetadata findFileByIdElseThrow(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File system", "ID", fileId));

    }

    private List<FolderResponse> findParents(UUID folderId) {

        List<FolderMetadata> folders = new LinkedList<>();
        FolderMetadata current = findFolderByIdElseThrow(folderId);
        while (current != null) {
            folders.add(current);
            current = current.getParent();
        }
        return mapper.map(folders);
    }

    private FolderMetadata findRoot() {
        return folderRepository.findByParent(null).orElseThrow(() -> new ResourceNotFoundException("Folder metadata", "Root", ""));
    }


    public static void main(String[] args) {
        System.out.println(FilenameUtils.getExtension("123"));
    }
}


