import { Theme } from "@mui/material";

export default {
  paper: {
    padding: 4,
  },

  formGroup: {
    marginY: 2,
    flexWrap: "unset",
  },

  formLabel: {
    color: (theme: Theme) => theme.palette.text.primary,
    marginBottom: 0.5,
  },
};
