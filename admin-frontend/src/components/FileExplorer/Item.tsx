import {FileMetadata, FileSystemMetadata} from "@models/FileSystemMetadata";
import {
    Box,
    Checkbox,
    IconButton,
    TableCell,
    TableRow,
    Theme,
} from "@mui/material";
import FolderOutlinedIcon from "@mui/icons-material/FolderOutlined";
import AudioFileOutlinedIcon from "@mui/icons-material/AudioFileOutlined";
import React, {MouseEvent, useMemo} from "react";
import {useAppDispatch} from "./redux/store";
import {getFolder} from "./redux/explorerSlice";
import {useExplorerAction, useExplorerSelector} from "./redux";
import style from "./style";
import {FileType} from "@models/FileType";
import InsertDriveFileOutlinedIcon from "@mui/icons-material/InsertDriveFileOutlined";
import {CheckBox} from "@mui/icons-material";
import ArticleOutlinedIcon from "@mui/icons-material/ArticleOutlined";
import ImageOutlinedIcon from "@mui/icons-material/ImageOutlined";

interface ItemProps {
    file: FileSystemMetadata;
    handleOpenContextMenu: (x: number, y: number) => void;
}

export const GridItem = (props: ItemProps) => {
    const dispatch = useAppDispatch();
    const {setSelectedId} = useExplorerAction();
    const {selectedIds} = useExplorerSelector();
    const {file} = props;

    function handleClick(event: MouseEvent<HTMLElement>): void {
        switch (event.detail) {
            case 1:
                handleSelect();
                break;
            case 2:
                if (file.isFolder) handleOpen();
                break;
        }
    }

    function handleSelect() {
        dispatch(setSelectedId(props.file.id));
    }

    function handleOpen() {
        dispatch(getFolder(props.file.id));
    }

    const icon = useMemo<JSX.Element>(() => {
        if (file.isFolder) {
            return (
                <Box sx={style.iconWrapper}>
                    <FolderOutlinedIcon sx={{fontSize: 48}}/>
                </Box>
            );
        }

        const temp = file as FileMetadata;

        if (temp.type == FileType.IMAGE) {
            return (
                <Box sx={style.iconWrapper}>
                    <img src={temp.thumbnail} style={style.thumbnail}/>
                </Box>
            );
        } else if (temp.type == FileType.AUDIO) {
            return (
                <Box sx={style.iconWrapper}>
                    <AudioFileOutlinedIcon sx={{fontSize: 48}}/>
                </Box>
            );
        }
        return <div></div>;
    }, [file.id]);

    const active = selectedIds.includes(file.id);

    return (
        <Box
            onClick={handleClick}
            sx={style.item(active)}
            onContextMenu={(e) => {
                e.preventDefault();
                if (!active) handleSelect();
                console.log(e);

                props.handleOpenContextMenu(e.clientX, e.clientY);
            }}>
            <Box width={"100%"}>
                {icon}
                <Box sx={style.itemName}>
                    {file.basename ? file.basename : file.name}
                </Box>
            </Box>
        </Box>
    );
};

export const TableItem = (props: ItemProps) => {
    const {file} = props;
    const {setSelectedId} = useExplorerAction();
    const {selectedIds} = useExplorerSelector();
    const dispatch = useAppDispatch();

    const selected = selectedIds.includes(file.id);

    function handleClick(event: MouseEvent<HTMLElement>): void {
        switch (event.detail) {
            case 1:
                handleSelect();
                break;
            case 2:
                if (file.isFolder) handleOpen();
                break;
        }
    }

    const icon = useMemo<JSX.Element>(() => {
        if (file.isFolder) {
            return <FolderOutlinedIcon/>;
        }

        const temp = file as FileMetadata;

        switch (temp.type) {
            case FileType.IMAGE:
                return <ImageOutlinedIcon/>;
            case FileType.DOCUMENT:
                return <ArticleOutlinedIcon/>;
            case FileType.AUDIO:
                return <AudioFileOutlinedIcon/>;
            default:
                return <InsertDriveFileOutlinedIcon/>;
        }
    }, [file.id]);

    const size = useMemo(() => {
        if (file.isFolder) return "";
        const temp = file as FileMetadata;

        return temp.size;
    }, [file.id]);

    function handleSelect() {
        dispatch(setSelectedId(props.file.id));
    }

    function handleOpen() {
        dispatch(getFolder(props.file.id));
    }

    return (
        <TableRow
            hover
            selected={selected}
            onClick={handleClick}
            onContextMenu={(e) => {
                e.preventDefault();
                if (!selected) handleSelect();
                console.log(e);

                props.handleOpenContextMenu(e.clientX, e.clientY);
            }}>
            <TableCell align={"center"} valign={"middle"}>
            </TableCell>
            <TableCell>{icon}</TableCell>
            <TableCell>{file.name}</TableCell>
            <TableCell></TableCell>
            <TableCell>{file.lastModifiedAt}</TableCell>
            <TableCell>{size}</TableCell>
        </TableRow>
    );
};
