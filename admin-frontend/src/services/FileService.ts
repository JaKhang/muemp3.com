import { ExplorerResponse } from "@models/ExplorerResponse.ts";
import { FileType } from "@models/FileType.ts";
import { Page } from "@models/Sort.ts";
import apiFetcher from "@services/ApiFetcher.ts";
import { AxiosProgressEvent } from "axios";
import { buildHeaders } from "./utils";

class FileService {
  baseEndpoint: string = "/api/v1/files";

  __generateEndpoint(...path: string[]) {
    return [this.baseEndpoint, ...path].join("/");
  }

  moveToTrash(id: string) {
    const endPoint = this.__generateEndpoint("move-to-trash", id);
    return apiFetcher.put(endPoint, {}, { headers: buildHeaders() });
  }

  moveFile(ids: string[], parentId: string, copy: boolean) {
    const endPoint = this.__generateEndpoint("move", ids[0]);
    return apiFetcher.put(
      endPoint,
      { parentId, name: "" },
      { headers: buildHeaders() }
    );
  }

  findFolder(folderId: string, page: Page, query: FileQuery) {
    const endpoint = this.__generateEndpoint("folders", folderId);
    return apiFetcher.get<ExplorerResponse>(endpoint, {
      params: {
        property: page.sort.property,
        direction: page.sort.direction,
        page: page.page,
        limit: page.limit,
        ...query,
      },
      headers: buildHeaders(),
    });
  }
  async findRoot() {
    const endpoint = this.__generateEndpoint("folders", "root");
    const rs = await apiFetcher.get<ExplorerResponse>(endpoint, {
      headers: buildHeaders(),
    });
    return rs;
  }

  createFolder(parentId: string, name: string) {
    const endpoint = this.__generateEndpoint("folders");
    return apiFetcher.post<never>(
      endpoint,
      { parentId, name },
      { headers: buildHeaders() }
    );
  }

  upload(
    folderId: string,
    file: File,
    onUploadProgress?: (progressEvent: AxiosProgressEvent) => void
  ) {
    const endpoint = this.__generateEndpoint("folders", folderId);
    const formData = new FormData();
    formData.append("file", file);
    console.log(file);
    
    const headers = buildHeaders();
    headers["Content-Type"] = "multipart/form-data";
    return apiFetcher.post(endpoint, formData, {
      onUploadProgress,
      headers,
    });
  }

  download(fileId: string) {
    const endpoint = this.__generateEndpoint("download", fileId);
    return apiFetcher.get<void>(endpoint);
  }
}
export interface FileQuery {
  fileType: FileType | null;
  name: string;
  deleted: boolean;
}
export default new FileService();
