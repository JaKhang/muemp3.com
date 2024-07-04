export default interface TrackRequest {
    name: string;
    alias: string;
    imageId: string;
    artistIds: string[];
    genreIds: string[];
    mvLink: string;
    isIndie: boolean;
    trackIndex: number;
    description: string;
    composerId: string;
    audioId: string;
    distributorId: string;
    albumId: string;
    status: string;
    lyrics: string;
    isOfficial: boolean;
    duration: number;
    releaseDate: string;
    process: boolean
}