import {AxiosHeaders} from "axios";
import {ArtistLine, ArtistRequest} from "../models/Artist.ts";
import {ArtistType} from "../models/ArtistType.ts";
import api from "./api.ts";

const BASE = "/api/v1/artists"

class ArtistService{
    async post(request: ArtistRequest) {
        return await api.post<ArtistLine>(BASE, request);
    }

    async postWithImage(thumbnail: File, info : ArtistRequest){
        const formData = new FormData();
        formData.append('thumbnail', thumbnail, thumbnail.name)
        formData.append('info', new Blob([JSON.stringify(info)], {
            type: "application/json"
        }));
        const headers = new AxiosHeaders();
        headers.setContentType("multipart/form-data");
        return api.post<ArtistLine>(`${BASE}/include-image`, formData, {headers})
    }

    async getAllType(){
        const endpoint = `${BASE}/types`
        return await api.get<ArtistType[]>(endpoint);
    }
}

export default new ArtistService();