import axios, {AxiosRequestConfig} from "axios";
import ApiResponse from "../models/ApiResonse.ts";

const DOMAIN = 'http://localhost:8080'

const request = axios.create({
    proxy: false,
    baseURL: DOMAIN

})

// export const getAuthHeader = () => {
//     let token = localStorage.getItem(Key.ACCESS_TOKEN)
//     if(token){
//         return `Bearer ${token}`;
//     } else {
//         return null;
//     }
// }

export default {
    async get<T>(endpoint: string, option?: AxiosRequestConfig<any>): Promise<ApiResponse<T>>{
        console.log(request.defaults.headers)
        const response = await request.get(endpoint, option);
        return response.data;
    },
    async post<T>(endpoint: string, data?: any,option?: AxiosRequestConfig<any>): Promise<ApiResponse<T>>{
        const response = await request.post(endpoint, data, option);
        return response.data;
    },
    async delete<T>(endpoint: string, option?: AxiosRequestConfig<any>) : Promise<ApiResponse<T>>{
        return request.delete(endpoint, option)
    },
    setDefaultHeader(key: string, data?: string){
        request.defaults.headers.common[key] = data;
    }

};