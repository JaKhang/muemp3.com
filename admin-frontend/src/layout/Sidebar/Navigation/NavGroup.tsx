import React from "react";
import NavProps from "./NavProps";
import { List, ListSubheader, Typography } from "@mui/material";
import { MenuItemType } from "../../../menu/MenuItem";
import NavItem from "./NavItem";
import NavCollapse from "./NavCollapse";
import WorkspacesRoundedIcon from "@mui/icons-material/WorkspacesRounded";
import { useLayoutSelector } from "../../../redux/selector";
import style from "./style";

const NavGroup = ({ item, level }: NavProps) => {
  const { slim } = useLayoutSelector();

  const items = item.children?.map((i) => {
    switch (i.type) {
      case MenuItemType.ITEM:
        return <NavItem key={i.id} item={i} level={level + 1} />;
      case MenuItemType.COLLAPSE:
        return <NavCollapse key={i.id} item={i} level={level + 1} />;
      default:
        return (
          <Typography key={i.id} variant="h6" color="error" align="center">
            Fix - Navigation Group
          </Typography>
        );
    }
  });
  return (
    <List
      sx={style.navBar()}
      component="nav"
      subheader={
        <ListSubheader
          component="div"
          sx={style.navSubHeader(slim)}
          disableSticky>
          {!slim ? item.title : <WorkspacesRoundedIcon sx={{ fontSize: 8 }} />}
        </ListSubheader>
      }>
      {items}
    </List>
  );
};

export default NavGroup;
