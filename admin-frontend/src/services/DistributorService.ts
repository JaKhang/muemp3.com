import apiFetcher from "@services/ApiFetcher.ts";
import Distributor from "@models/Distributor.ts";
import InfiniteList from "@models/InfiniteList.ts";

class DistributorService {
    async findAll() {
        return await apiFetcher.get<InfiniteList<Distributor>>("/api/v1/distributors");
    }
}

export default new DistributorService();