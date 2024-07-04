import { Children } from "react";
import MenuItem, { MenuItemType } from "./MenuItem";
import SendIcon from '@mui/icons-material/Send';
import { Icon } from "@mui/material";
import FolderOpenOutlinedIcon from '@mui/icons-material/FolderOpenOutlined';


export default {
    title: "File",
    id: "file",
    type: MenuItemType.GROUP,
    url: "",
    children: [
        {
            id: "file-explorer",
            title: "File Explorer",
            url: "/file-explorer",
            type:  MenuItemType.ITEM,
            icon: FolderOpenOutlinedIcon,
        }
    ]
    
} as MenuItem