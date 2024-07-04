import React, { LegacyRef, forwardRef } from "react";
import NavProps from "./NavProps";
import { Link, useLocation } from "react-router-dom";

import {
  Collapse,
  Icon,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import Theme from "@themes/Theme";
import CircleOutlinedIcon from "@mui/icons-material/CircleOutlined";

import { useLayoutSelector } from "../../../redux/selector";
import style from "./style";
import { MenuItemType } from "../../../menu/MenuItem";

const NavItem = ({ item, level }: NavProps) => {
  const { pathname } = useLocation();
  const { slim } = useLayoutSelector();

  let itemTarget = "_self";
  if (item.target) {
    itemTarget = "_blank";
  }
  let listItemProps;
  if (item?.external) {
    listItemProps = { component: "a", href: item.url, target: itemTarget };
  } else {
    listItemProps = {
      component: forwardRef((props, ref: LegacyRef<HTMLAnchorElement>) => (
        <Link ref={ref} {...props} to={item.url} target={itemTarget} />
      )),
    };
  }

  let active = item.url == pathname;

  return (
    <>
      <ListItemButton
        {...listItemProps}
        sx={style.navItem(slim, level, active)}
        disableRipple>
        <ListItemIcon sx={style.navItemIcon(slim)}>
          {item.icon ? (
            <Icon
              component={item.icon}
              sx={{ font: "inherit", margin: 0, minWidth: "unset" }}
            />
          ) : (
            <CircleOutlinedIcon sx={{ fontSize: 8, minWidth: "unset" }} />
          )}
        </ListItemIcon>
        {!slim && (
          <ListItemText sx={style.navItemText(slim)}>{item.title}</ListItemText>
        )}
      </ListItemButton>
    </>
  );
};

export default NavItem;
