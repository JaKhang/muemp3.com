import ObjectResponse from "@models/ObjectResponse.ts";
import apiFetcher from "@services/ApiFetcher.ts";
import {buildHeaders} from "@services/utils.ts";

class CountryService {


    async findAll() {
        return await apiFetcher.get<ObjectResponse[]>("/api/v1/countries", {
            headers: buildHeaders(),
        });
    }


}

export default new CountryService();