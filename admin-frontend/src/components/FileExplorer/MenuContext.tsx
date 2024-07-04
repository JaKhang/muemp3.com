import Divider from "@mui/material/Divider";
import Paper from "@mui/material/Paper";
import MenuList from "@mui/material/MenuList";
import MenuItem from "@mui/material/MenuItem";
import ListItemText from "@mui/material/ListItemText";
import ListItemIcon from "@mui/material/ListItemIcon";
import Typography from "@mui/material/Typography";
import ContentCut from "@mui/icons-material/ContentCut";
import ContentCopy from "@mui/icons-material/ContentCopy";
import ContentPaste from "@mui/icons-material/ContentPaste";
import Cloud from "@mui/icons-material/Cloud";
import { useEffect } from "react";
import { useExplorerAction, useExplorerSelector } from "./redux";
import { useAppDispatch } from "@redux/store";
import DeleteIcon from "@mui/icons-material/Delete";

interface MenuContextProp {
  x: number;
  y: number;
}

const MenuContext = ({ x, y }: MenuContextProp) => {
  const { addToClipboard } = useExplorerAction();
  const dispatch = useAppDispatch();

  const handleCopy = () => {
    dispatch(addToClipboard(true));
  };

  const handleCut = () => {
    dispatch(addToClipboard(false));
  };

  return (
    <Paper
      sx={{
        width: 320,
        maxWidth: "100%",
        top: y,
        left: x,
        position: "absolute",
        zIndex: 100,
        backgroundColor: "white",
      }}>
      <MenuList>
        <MenuItem onClick={handleCut}>
          <ListItemIcon>
            <ContentCut fontSize="small" />
          </ListItemIcon>
          <ListItemText>Cut</ListItemText>
          <Typography variant="body2" color="text.secondary">
            ⌘X
          </Typography>
        </MenuItem>
        <MenuItem onClick={handleCopy}>
          <ListItemIcon>
            <ContentCopy fontSize="small" />
          </ListItemIcon>
          <ListItemText>Copy</ListItemText>
          <Typography variant="body2" color="text.secondary">
            ⌘C
          </Typography>
        </MenuItem>
        <MenuItem>
          <ListItemIcon>
            <ContentPaste fontSize="small" />
          </ListItemIcon>
          <ListItemText>Paste</ListItemText>
          <Typography variant="body2" color="text.secondary">
            ⌘V
          </Typography>
        </MenuItem>
        <MenuItem>
          <ListItemIcon>
            <DeleteIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText>Move to Trash</ListItemText>
        </MenuItem>
      </MenuList>
    </Paper>
  );
};

export default MenuContext;
