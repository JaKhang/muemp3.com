export interface Genre{
    id: string;
    name: string;
    thumbnail:string;
}
export interface GenreRequest{
    name: string;
    thumbnailId: string;
    alias: string;
}