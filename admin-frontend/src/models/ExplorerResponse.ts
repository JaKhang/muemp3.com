import {FileSystemMetadata, FolderMetadata} from "@models/FileSystemMetadata.ts";


export interface ExplorerResponse {
    current: FolderMetadata;
    parents: FolderMetadata[];
    files: FileSystemMetadata[];
    hasMore: boolean
}