import { Box, Card, Paper } from "@mui/material";
import React, { useEffect } from "react";
import Content from "./Content";
import Header from "./Header";
import Preview from "./Preview";
import { useAppDispatch } from "./redux/store";
import { getRoot } from "./redux/explorerSlice";
import { FileExplorerProps } from ".";
import { useExplorerAction, useExplorerSelector } from "./redux";
import { FileMetadata } from "@models/FileSystemMetadata";

const Explorer = (props: FileExplorerProps) => {
  const { selectedIds, data } = useExplorerSelector();
  const { onSelectFiles, onSelectFolders, multiple, filter } = props;
  const { setMultiple, setFileType,  } = useExplorerAction();
  const dispatch = useAppDispatch();
  useEffect(() => {
    //@ts-ignore
    dispatch(getRoot())
      .unwrap()
      .then((res) => console.log(res));

    dispatch(setMultiple(!!multiple));
    dispatch(setFileType(filter ? filter : null));
  }, []);

  useEffect(() => {
    if (!data) return;
    if (onSelectFiles) {
      let files = data.files.filter(
        (file) => !file.isFolder && selectedIds.includes(file.id)
      );
      onSelectFiles(files as FileMetadata[]);
    }
    if (onSelectFolders) {
    }
  }, [selectedIds]);

  return (
    <Paper square variant="outlined">
      <Header showFilter={!filter} />
      <Content />
      <Preview />
    </Paper>
  );
};

export default Explorer;
