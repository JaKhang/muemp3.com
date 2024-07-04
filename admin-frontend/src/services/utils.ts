import { Key } from "@constants/Key";
import { AxiosHeaders } from "axios";

export const buildHeaders = (headers?: any) => {
  const axiosHeaders = new AxiosHeaders();
  for (let key in headers) {
    axiosHeaders.set(key, headers[key]);
  }
  let accessToken = localStorage.getItem(Key.ACCESS_TOKEN);
  if (accessToken) {
    axiosHeaders.set(Key.AUTHORIZATION, `Bearer ${accessToken}`);
  }
  return axiosHeaders;
};
