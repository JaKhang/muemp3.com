export interface ArtistLine {
    id: string;
    alias: string;
    name: string;
    thumbnail: string;
    role: string;
}

export interface ArtistRequest {
    name : string;
    alias : string;
    isOfficial: boolean;
    isIndie : boolean;
    birthday: string;
    description: string;
    typeIds: string[];
    thumbnailId: string;
    thumbnail?: File;
}

export interface ArtistDetails {

}