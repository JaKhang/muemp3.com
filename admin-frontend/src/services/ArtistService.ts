import apiFetcher from "@services/ApiFetcher.ts";
import ArtistRequest from "@models/ArtistRequest";
import { buildHeaders } from "./utils";
import ObjectResponse from "@models/ObjectResponse";
import InfiniteList from "@models/InfiniteList";
import Artist from "@models/Artist";

class ArtistService {
  async createArtist(request: ArtistRequest) {
    return await apiFetcher.post<unknown>("/api/v1/artists", request, {
      headers: buildHeaders({ "Content-Type": "application/json" }),
    });
  }

  async getTypes() {
    return await apiFetcher.get<ObjectResponse[]>("/api/v1/artists/types", {
      headers: buildHeaders(),
    });
  }

  async findAll() {
    return await apiFetcher.get<InfiniteList<Artist>>("/api/v1/artists", {
      headers: buildHeaders(),
    });
  }
}

export default new ArtistService();
