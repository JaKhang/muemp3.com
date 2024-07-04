import { Theme } from "@mui/material/styles";

export default {
  navBar: () => {
    return {
      width: "100%",
      maxWidth: 360,
      bgcolor: "background.paper",
      marginBottom: 1.5,
      "&:last-child": {
        marginBottom: "0",
      },
    };
  },
  navItem: (slim: boolean, level: number, active: boolean) => {
    const common = {
      color: (theme: Theme) =>
        active
          ? theme.palette.primary["main"]
          : theme.palette.secondary["dark"],
      backgroundColor: (theme: Theme) =>
        active ? theme.palette.primary.lighter : "transparent",
      overflow: "hidden",
      bgColor: (theme: Theme) => theme.palette.primary.lighter,
      borderRight:
        active && !slim
          ? (theme: Theme) => `2px solid ${theme.palette.primary.main}`
          : "unset",
    };

    if (slim)
      return {
        ...common,
        padding: 0,
        marginX: "auto",
        width: "36px",
        height: "36px",
        display: "flex",
        justifyContent: "center",
        borderRadius: "4px",
        marginBottom: "8px",
        "&:last-child": {
          marginBottom: "0",
        },
      };

    return {
      ...common,
      paddingY: 1,

      paddingLeft: 3 + level,
    };
  },
  navItemIcon: (slim: boolean) => {
    return {
      fontSize: slim ? 24 : 18,
      color: "inherit",
      margin: 0,
      marginRight: slim ? 0 : 2,
      justifyContent: "center",
    };
  },
  navItemText: (slim: boolean) => {
    return {
      fontSize: 18,
      color: "inherit",
      overflow: "hidden",
      whiteSpace: "normal",
    };
  },
  navSubHeader: (slim: boolean) => {
    return {
      fontSize: 12,
      lineHeight: 1,
      marginBottom: "12px",
      color: (theme: Theme) => theme.palette.grey[500],
      textAlign: slim ? "center" : null,
    };
  },
  expandIcon: {
    fontSize: 16,
    color: "#262626",
  },
  collapseItem: (slim: boolean, level: number, active: boolean) => {
    const common = {
      color: (theme: Theme) =>
        active
          ? theme.palette.primary["main"]
          : theme.palette.secondary["dark"],

      overflow: "hidden",
      bgColor: (theme: Theme) => theme.palette.primary.lighter,
    };

    if (slim)
      return {
        ...common,
        padding: 0,
        marginX: "auto",
        width: "36px",
        height: "36px",
        display: "flex",
        justifyContent: "center",
        borderRadius: "4px",
        marginBottom: "8px",
        "&:last-child": {
          marginBottom: "0",
        },
      };

    return {
      ...common,
      paddingY: 1,
      paddingLeft: 3 + level,
    };
  },
};
