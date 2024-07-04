import ApiResponse from "@models/ApiResponse.ts";
import axios, { AxiosRequestConfig } from "axios";

//@ts-ignore
// const DOMAIN = "https://api.muemp3.site";
const DOMAIN = "http://localhost:8080";

const request = axios.create({
  proxy: false,
  baseURL: DOMAIN,
});
request.interceptors.response.use(
  function (response) {
    return response.data;
  },
  function (error) {
    console.log(error);
    if (error === null) throw new Error("Unrecoverable error!! Error is null!");
    if (axios.isAxiosError(error)) {
      //here we have a type guard check, error inside this if will be treated as AxiosError
      const response = error?.response;
      const request = error?.request;
      const config = error?.config; //here we have access the config used to make the api call (we can make a retry using this conf)

      if (error.code === "ERR_NETWORK") {
        console.log("connection problems..");
      } else if (error.code === "ERR_CANCELED") {
        console.log("connection canceled..");
      }
      if (response) {
        return Promise.reject(response.data);
      } else if (request) {
        //The request was made but no response was received, `error.request` is an instance of XMLHttpRequest in the browser and an instance of http.ClientRequest in Node.js
      }
    }
    //Something happened in setting up the request and triggered an Error
  }
);

export default {
  async get<T>(
    endpoint: string,
    option?: AxiosRequestConfig<never>
  ): Promise<ApiResponse<T>> {
    return await request.get(endpoint, option);
  },
  async post<T>(
    endpoint: string,
    data?: any,
    option?: AxiosRequestConfig<never>
  ): Promise<ApiResponse<T>> {
    return await request.post(endpoint, data, option);
  },
  async put<T>(
    endpoint: string,
    data?: any,
    option?: AxiosRequestConfig<never>
  ): Promise<ApiResponse<T>> {
    return await request.put(endpoint, data, option);
  },
  async delete<T>(
    endpoint: string,
    option?: AxiosRequestConfig<never>
  ): Promise<ApiResponse<T>> {
    return request.delete(endpoint, option);
  },
  setDefaultHeader(key: string, data?: string) {
    request.defaults.headers.common[key] = data;
  },
};
