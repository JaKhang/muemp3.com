import TrackRequest from "@models/TrackRequest.ts";
import apiFetcher from "@services/ApiFetcher.ts";
import {buildHeaders} from "@services/utils.ts";

class TrackService{
    async create (request: TrackRequest){

        return await apiFetcher.post<unknown>("/api/v1/tracks", request, {
            headers: buildHeaders({ "Content-Type": "application/json" }),
        });    }
}

export default new TrackService();