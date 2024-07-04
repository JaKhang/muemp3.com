import {
  Box,
  ButtonGroup,
  CardHeader,
  Divider,
  IconButton,
  MenuItem,
  OutlinedInput,
  Paper,
  Select,
  SelectChangeEvent,
  ToggleButton,
  ToggleButtonGroup,
  Typography,
} from "@mui/material";
import React, { useRef, useState } from "react";
import CreateNewFolderOutlinedIcon from "@mui/icons-material/CreateNewFolderOutlined";
import UploadOutlinedIcon from "@mui/icons-material/UploadOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import FormatListBulletedOutlinedIcon from "@mui/icons-material/FormatListBulletedOutlined";
import { useExplorerAction, useExplorerSelector } from "./redux";
import { ViewMode } from "./ViewMode";
import style from "./style";
import { useAppDispatch } from "./redux/store";
import RefreshOutlinedIcon from "@mui/icons-material/RefreshOutlined";
import { Direction, Sort } from "@models/Sort";
import { getFolder, getRoot, loadMore, moveTo } from "./redux/explorerSlice";
import ExplorerDialog from "./ExplorerDialog";
import fileService from "@services/FileService";
import { enqueueSnackbar } from "notistack";
import { ContentPaste } from "@mui/icons-material";

interface HeaderProps {
  showFilter?: boolean;
}

const Header = (props: HeaderProps) => {
  const { viewMode, page, data, clipboard } = useExplorerSelector();
  const { setViewMode, setPage } = useExplorerAction();
  const dispatch = useAppDispatch();

  const [folderModal, setFolderModal] = useState(false);
  const [folderName, setFolderName] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);

  function handleChangeViewMode(
    event: React.MouseEvent<HTMLElement>,
    nextView: ViewMode
  ): void {
    dispatch(setViewMode(nextView));
  }

  function refresh() {
    dispatch(getFolder(data.current.id));
  }

  function handleChangeSort(event: SelectChangeEvent): void {
    let property = event.target.value.split("-")[0];
    let direction = event.target.value.split("-")[1];
    let sort = {
      //@ts-ignore
      direction: Direction[direction],
      property,
    };

    dispatch(setPage({ ...page, sort, page: 1 }));
    //@ts-ignore
    dispatch(getFolder(data?.current.id));
  }

  function handleUpload(e: React.ChangeEvent<HTMLInputElement>) {
    let files = e.target.files as FileList;
    for (let file of files) {
      fileService
        .upload(data.current.id, file)
        .then((res) => enqueueSnackbar(res.message, { variant: "success" }))
        .catch((res) => enqueueSnackbar(res.message, { variant: "error" }));
    }
  }

  function handleSubmit(): void {
    if (folderName) {
      fileService
        .createFolder(data.current.id, folderName)
        .then((res) => enqueueSnackbar(res.message, { variant: "success" }));
      setFolderName("");
      setFolderModal(false);
    }
  }

  const handlePaste = () => {
    console.log("paste");

    //@ts-ignore
    dispatch(moveTo());
  };

  return (
    <Paper sx={style.header} variant="outlined" square elevation={0}>
      <Box sx={style.headerGroup}>
        <IconButton color="primary" onClick={() => setFolderModal(true)}>
          <CreateNewFolderOutlinedIcon sx={{ fontSize: 18 }} />
        </IconButton>
        <IconButton onClick={() => inputRef.current?.click()}>
          <UploadOutlinedIcon sx={{ fontSize: 18 }} />
        </IconButton>
        <input
          onChange={handleUpload}
          ref={inputRef}
          type="file"
          hidden
          multiple={true}
        />
      </Box>
      <Box sx={style.headerGroup}>
        {/* {props.showFilter && (
          <Select input={<OutlinedInput size="small" />}>
            <MenuItem value="createdAt-DESC">Date (DESC)</MenuItem>
            <MenuItem value="createdAt-ASC">Date (ASC)</MenuItem>
            <MenuItem value="name-DESC">Name (DESC)</MenuItem>
            <MenuItem value="name-ASC">Name (ASC)</MenuItem>
          </Select>
        )} */}
      </Box>
      <Box sx={style.headerGroup}>
        {clipboard && (
          <IconButton onClick={() => handlePaste()}>
            <ContentPaste fontSize="small" />
          </IconButton>
        )}
        <Select
          input={<OutlinedInput size="small" />}
          onChange={handleChangeSort}
          value={`${page.sort.property}-${page.sort.direction}`}>
          <MenuItem value="createdAt-DESC">Date (DESC)</MenuItem>
          <MenuItem value="createdAt-ASC">Date (ASC)</MenuItem>
          <MenuItem value="name-DESC">Name (DESC)</MenuItem>
          <MenuItem value="name-ASC">Name (ASC)</MenuItem>
        </Select>
        <Divider orientation="vertical" variant="middle" flexItem />
        <ToggleButtonGroup
          value={viewMode}
          onChange={handleChangeViewMode}
          exclusive
          size="small">
          <ToggleButton
            value={ViewMode.GRID}
            sx={{ border: "none ! important" }}>
            <GridViewOutlinedIcon sx={{ fontSize: 18 }} />
          </ToggleButton>

          <ToggleButton value={ViewMode.LIST} sx={{ border: "none" }}>
            <FormatListBulletedOutlinedIcon sx={{ fontSize: 18 }} />
          </ToggleButton>
        </ToggleButtonGroup>
        <Divider orientation="vertical" variant="middle" flexItem />
        <IconButton onClick={refresh}>
          <RefreshOutlinedIcon sx={{ fontSize: 18 }} />
        </IconButton>
      </Box>
      <ExplorerDialog
        handleClose={() => setFolderModal(false)}
        handleSubmit={handleSubmit}
        open={folderModal}
        title={<Typography variant="h5">Create Folder</Typography>}>
        <Box sx={{ paddingX: 2 }}>
          <OutlinedInput
            sx={{ width: "280px", marginY: 1 }}
            value={folderName}
            onChange={(e) => setFolderName(e.target.value)}
            placeholder="Folder Name"
          />
        </Box>
      </ExplorerDialog>
    </Paper>
  );
};

export default Header;
