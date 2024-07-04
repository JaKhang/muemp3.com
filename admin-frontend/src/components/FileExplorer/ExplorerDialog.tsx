import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

interface DialogProps {
  handleClose: () => void;
  open: boolean;
  children: JSX.Element;
  title?: JSX.Element | string;
  handleSubmit: () => void;
}

const ExplorerDialog = (props: DialogProps) => {
  const { handleClose, open, children, title, handleSubmit } = props;
  return (
    <Dialog disableEscapeKeyDown open={open} onClose={handleClose}>
      {title && <DialogTitle sx={{ padding: 2 }}>{title}</DialogTitle>}
      <DialogContent sx={{ padding: 0 }}>{children}</DialogContent>
      <DialogActions>
        {handleClose && <Button onClick={handleClose}>Cancel</Button>}
        {handleSubmit && <Button onClick={handleSubmit}>Ok</Button>}
      </DialogActions>
    </Dialog>
  );
};

export default ExplorerDialog;
