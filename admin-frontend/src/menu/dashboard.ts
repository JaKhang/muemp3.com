import { Children } from "react";
import MenuItem, { MenuItemType } from "./MenuItem";
import DashboardOutlinedIcon from "@mui/icons-material/DashboardOutlined";
export default {
  title: "Dashboard",
  id: "dashboardGroup",
  type: MenuItemType.GROUP,
  url: "",
  children: [
    {
      id: "dashboard",
      title: "Dashboard",
      type: MenuItemType.ITEM,
      icon: DashboardOutlinedIcon,
      url: "/dashboard",
    },
  ],
} as MenuItem;
