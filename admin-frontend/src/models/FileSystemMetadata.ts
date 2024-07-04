export interface FileSystemMetadata {
    name: string;
    isFolder: boolean;
    lastModifiedAt: string;
    createdAt: string;
    parentId: string;
    fileId: string
    id: string
    basename?: string


}
export interface FileMetadata extends FileSystemMetadata{
    basename: string;
    previewUrl:string;
    mimetype: string;
    thumbnail: string;
    url: string;
    type: string;
    icon: string;
    size: string;
    alt: string;
}

export interface FolderMetadata extends FileSystemMetadata{

}