import { Theme } from "@mui/material";

export default {
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: 1,
  },
  headerGroup: {
    display: "flex",
    gap: 1,
    alignItems: "center",
  },
  contentContainer: {
    position: "relative",
    minHeight: "400px",
    maxHeight: "420px",
    overflow: "auto",
  },
  gridContent: {
    padding: 2,
    display: "grid",
    gridTemplateColumns: " repeat(8, 1fr);",
    gap: 1,
    overflow: "show",
  },
  item: (active: boolean) => {
    return {
      aspectRatio: "6/7",
      overflow: "hidden",
      borderRadius: "2px",
      backgroundColor: active
        ? (theme: Theme) => `${theme.palette.primary.lighter} !important`
        : undefined,
      "&:hover": {
        backgroundColor: (theme: Theme) => theme.palette.action.hover,
      },
      color: active ? (theme: Theme) => theme.palette.primary.main : undefined,
      cursor: "pointer",
    };
  },
  iconWrapper: {
    aspectRatio: "1/1",
    display: "grid",
    placeItems: "center",
  },
  thumbnail: {
    width: "100%",
    padding: "4px",
  },
  itemName: {
    textAlign: "center",
    whiteSpace: "nowrap",
    textOverflow: "ellipsis",
    overflow: "hidden",
    maxWidth: "100%",
    fontSize: 12,
    paddingX: 1,
  },
};
