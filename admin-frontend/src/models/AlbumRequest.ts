export enum AlbumType {
    STUDIO = "STUDIO",
    SINGLE = "SINGLE",
    COMPILATION = "COMPILATION",
    EP= "EP",
    LIVE = "LIVE",
    REMIX = "REMIX"
}

export default interface AlbumRequest {


    name: string

    alias: string
    description: string
    shortDescription: string

    artistIds: string[]
    trackIds: string[]
    genreIds: string[]
    imageId: string
    distributorId: string
    type: AlbumType;
    releaseDate: Date
    isIndie: boolean
}