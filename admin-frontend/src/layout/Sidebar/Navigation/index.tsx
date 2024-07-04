import React from "react";
import menu from "../../../menu";
import { MenuItemType } from "../../../menu/MenuItem";
import { Typography } from "@mui/material";
import NavGroup from "./NavGroup";

const Navigation = () => {
  const items = menu.map((item) => {
    switch (item.type) {
      case MenuItemType.GROUP:
        return <NavGroup key={item.id} item={item} level={0} />;
      default:
        return (
          <Typography key={item.id} variant="h6" color="error" align="center">
            Fix - Navigation Group
          </Typography>
        );
    }
  });

  return <div className="side-bar__nav">{items}</div>;
};

export default Navigation;
