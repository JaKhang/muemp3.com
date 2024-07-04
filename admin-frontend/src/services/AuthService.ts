import { LoginRequest } from "@models/LoginRequest";
import LoginResponse from "@models/LoginResponse";
import { AxiosHeaders } from "axios";
import apiFetcher from "./ApiFetcher";
import { buildHeaders } from "./utils";
import UserPrinciple from "@models/UserPrinciple";

const BASE_END_POINT = "/api/v1/auth";
class AuthService {
  async loginWithLocal(request: LoginRequest) {
    const headers = new AxiosHeaders();
    headers.set("Content-Type", "application/json");
    return await apiFetcher.post<LoginResponse>(BASE_END_POINT, request, {
      headers,
    });
  }

  async getPrinciple(token: string) {
    const headers = buildHeaders({
      "Content-Type": "application/json",
    });
    console.log(headers);

    return await apiFetcher.get<UserPrinciple>("/api/v1/me", { headers });
  }
}

export default new AuthService();
