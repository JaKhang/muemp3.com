import apiFetcher from "@services/ApiFetcher.ts";
import {buildHeaders} from "@services/utils.ts";
import AlbumRequest from "@models/AlbumRequest.ts";
import InfiniteList from "@models/InfiniteList.ts";
import Album from "@services/Album.ts";

class AlbumService {
    async findAll(){
        return await apiFetcher.get<InfiniteList<Album>>("/api/v1/albums", {
            headers: buildHeaders({ "Content-Type": "application/json" }),
        });
    }

    async create(request: AlbumRequest){
        return await apiFetcher.post<unknown>("/api/v1/albums", request, {
            headers: buildHeaders({ "Content-Type": "application/json" }),
        });
    }
}

export default new AlbumService();