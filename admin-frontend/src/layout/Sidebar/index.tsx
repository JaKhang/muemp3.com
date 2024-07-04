import React from "react";
import "./style.scss";
import clsx from "clsx";
import { useLayoutSelector } from "../../redux/selector";
import Navigation from "./Navigation";
import { Box } from "@mui/material";

const SideBar = () => {
  const { slim } = useLayoutSelector();

  return (
    <div className={clsx("side-bar__wrapper", slim && "slim")}>
      <div className="side-bar__inner">
        <div className="side-bar__header">
          <div className="side-bar__logo">
            <img className="side-bar__logo-image" src="/logo.svg" />
            <div className="side-bar__logo-text">Mue</div>
          </div>
        </div>
        <Box className="side-bar__body" sx={{ paddingY: 2 }}>
          <Navigation />
        </Box>
        <div className="side-bar__footer"></div>
      </div>
    </div>
  );
};

export default SideBar;
