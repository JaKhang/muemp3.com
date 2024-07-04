import React from "react";
import NavProps from "./NavProps";
import {
  Collapse,
  Hidden,
  Icon,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Typography,
} from "@mui/material";
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";
import { MenuItemType } from "../../../menu/MenuItem";
import NavItem from "./NavItem";
import { useLayoutSelector } from "../../../redux/selector";
import style from "./style";
import { useLocation } from "react-router-dom";

const NavCollapse = ({ item, level }: NavProps) => {
  const [open, setOpen] = React.useState(false);
  const { slim } = useLayoutSelector();
  const { pathname } = useLocation();

  const active = pathname.startsWith(item.url);

  const handleClick = () => {
    setOpen(!open);
  };
  return (
    <>
      <ListItemButton
        onClick={handleClick}
        sx={style.collapseItem(slim, level, open)}>
        {item.icon && (
          <ListItemIcon sx={style.navItemIcon(slim)}>
            <Icon
              component={item.icon}
              sx={{ font: "inherit", margin: 0, minWidth: "unset" }}
            />
          </ListItemIcon>
        )}
        {!slim && (
          <ListItemText sx={style.navItemText(slim)}>{item.title}</ListItemText>
        )}
        {!slim &&
          (open ? (
            <ExpandLess sx={style.expandIcon} />
          ) : (
            <ExpandMore sx={style.expandIcon} />
          ))}
      </ListItemButton>
      <Collapse in={open} timeout="auto" unmountOnExit>
        <List component="div" sx={{ ...style.navBar(), paddingY: 0 }}>
          {item.children?.map((i) => {
            switch (i.type) {
              case MenuItemType.ITEM:
                return <NavItem key={i.id} item={i} level={level + 1} />;
              case MenuItemType.COLLAPSE:
                return <NavCollapse key={i.id} item={i} level={level + 1} />;
              default:
                return (
                  <Typography
                    key={i.id}
                    variant="h6"
                    color="error"
                    align="center">
                    Fix - Navigation Group
                  </Typography>
                );
            }
          })}
        </List>
      </Collapse>
    </>
  );
};

export default NavCollapse;
