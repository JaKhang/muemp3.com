import { Provider } from "react-redux";
import store from "./redux/store";
import Explorer from "./Explorer";
import { FileMetadata, FolderMetadata } from "@models/FileSystemMetadata";
import { FileType } from "@models/FileType";

export interface FileExplorerProps {
  multiple?: boolean;
  onSelectFiles?: (files: FileMetadata[]) => void;
  onSelectFolders?: (files: FolderMetadata[]) => void;
  filter?: FileType;
}

const FileExplorer = (props: FileExplorerProps) => {
  return (
    <Provider store={store}>
      <Explorer {...props} />
    </Provider>
  );
};

export default FileExplorer;
