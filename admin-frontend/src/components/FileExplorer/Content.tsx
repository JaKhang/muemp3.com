import React, { useCallback, useEffect, useMemo, useState } from "react";
import { useExplorerAction, useExplorerSelector } from "./redux";
import ArrowBackOutlinedIcon from "@mui/icons-material/ArrowBackOutlined";
import {
  Box,
  Breadcrumbs,
  Divider,
  FormControl,
  IconButton,
  Link,
  OutlinedInput,
  Paper,
  Typography,
} from "@mui/material";
import Loader from "../Loader";
import style from "./style";
import { useAppDispatch } from "@redux/store";
import { getFolder, loadMore } from "./redux/explorerSlice";
import { FolderMetadata } from "@models/FileSystemMetadata";
import SearchSharpIcon from "@mui/icons-material/SearchSharp";
import InfiniteScroll from "react-infinite-scroll-component";
import { ViewMode } from "./ViewMode";
import FileTable from "./FileTable";
import FileGrid from "./FileGrid";
import MenuContext from "./MenuContext";

const Content = () => {
  const { data, loading, viewMode, selectedIds } = useExplorerSelector();
  const { selectAll, setQueryName } = useExplorerAction();
  const [openContextMenu, setOpenContextMenu] = useState(false);
  const [point, setPoint] = useState({ x: 0, y: 0 });
  const dispatch = useAppDispatch();

  const [keyWord, setKeyWord] = useState("");

  useEffect(() => {
    const closeEvent = () => {
      setOpenContextMenu(false);
    };

    window.addEventListener("click", closeEvent);

    return () => {
      window.removeEventListener("click", closeEvent);
    };
  }, []);

  function handleBack(): void {
    if (data?.current.parentId)
      //@ts-ignore
      dispatch(getFolder(data.current.parentId));
  }

  const breadcrumbs = useMemo(() => {
    let temp = [] as FolderMetadata[];
    if (data) {
      temp = [...data.parents].reverse();
      temp.pop();
    }
    return temp;
  }, [data?.current.id]);

  function handleOpenFolder(folderId: string): void {
    //@ts-ignore
    dispatch(getFolder(folderId));
  }

  const handleRefresh = useCallback(() => {
    //@ts-ignore
    dispatch(getFolder(data.current.id));
  }, [data]);

  const handleLoadMore = useCallback(() => {
    //@ts-ignore
    dispatch(loadMore());
  }, []);

  const handleSelectAll = useCallback(() => {
    //@ts-ignore
    dispatch(selectAll());
  }, []);

  const handleOpenContextMenu = useCallback((x: number, y: number) => {
    setOpenContextMenu(true);
    setPoint({ x, y });
  }, []);

  function handleSearch(e: React.FormEvent<HTMLFormElement>): void {
    e.preventDefault();
    dispatch(setQueryName(keyWord));
    handleRefresh();
  }

  return (
    <>
      <Paper variant="outlined" square elevation={0}>
        <Paper
          sx={{
            paddingY: 1,
            paddingX: 2,
            borderTop: "none ! important",
            borderLeft: "",
            display: "flex",
            justifyContent: "space-between",
          }}
          variant="outlined"
          square
          elevation={0}>
          <Box sx={{ display: "flex", gap: 1, alignItems: "center" }}>
            <IconButton
              disabled={data?.parents.length == 1}
              onClick={handleBack}>
              <ArrowBackOutlinedIcon sx={{ fontSize: 18 }} />
            </IconButton>
            <Divider orientation="vertical" flexItem />
            <Breadcrumbs sx={{ marginLeft: 1 }}>
              {breadcrumbs.map((p) => (
                <Link
                  underline="hover"
                  color="inherit"
                  sx={{ cursor: "pointer" }}
                  onClick={() => handleOpenFolder(p.id)}
                  key={p.id}>
                  {p.name}
                </Link>
              ))}

              <Typography color="primary">{data?.current.name}</Typography>
            </Breadcrumbs>
          </Box>
          <Box component="form" onSubmit={(e) => handleSearch(e)}>
            <FormControl
              sx={{
                position: "relative",
              }}>
              <SearchSharpIcon
                sx={{
                  position: "absolute",
                  fontSize: 16,
                  top: "50%",
                  left: "12px",
                  transform: "translateY(-50%)",
                  color: "var(--mui-palette-text-disabled)",
                }}
              />
              <OutlinedInput
                size="small"
                placeholder="ctrl + k"
                fullWidth
                sx={{ paddingLeft: 3 }}
                onChange={(e) => setKeyWord(e.target.value)}
                value={keyWord}
              />
            </FormControl>
          </Box>
        </Paper>

        {viewMode == ViewMode.GRID ? (
          <FileGrid
            data={data}
            handleLoadMore={handleLoadMore}
            handleSelectAll={handleSelectAll}
            loading={loading}
            handleRefresh={handleRefresh}
            handleOpenContextMenu={handleOpenContextMenu}
          />
        ) : (
          <FileTable
            data={data}
            handleLoadMore={handleLoadMore}
            handleSelectAll={handleSelectAll}
            loading={loading}
            isSelectAll={selectedIds.length == data.files.length && !selectAll}
            handleRefresh={handleRefresh}
            handleOpenContextMenu={handleOpenContextMenu}
          />
        )}
      </Paper>
      {openContextMenu && <MenuContext x={point.x} y={point.y} />}
    </>
  );
};

export default Content;
