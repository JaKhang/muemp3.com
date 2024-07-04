import apiFetcher from "@services/ApiFetcher.ts";
import Genre from "@models/Genre.ts";

class GenreService{
    async findAll(){
        return await apiFetcher.get<Genre[]>("/api/v1/genres");
    }
}

export default new GenreService();