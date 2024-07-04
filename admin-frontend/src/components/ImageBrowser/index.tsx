import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  Paper,
  Typography,
} from "@mui/material";
import { Theme } from "@mui/material/styles";
import React, { forwardRef, useCallback, useState } from "react";
import FileExplorer from "../FileExplorer";
import { FileType } from "@models/FileType";
import { FileMetadata } from "@models/FileSystemMetadata";
import { Swiper, SwiperSlide } from "swiper/react";
import AddPhotoAlternateOutlinedIcon from "@mui/icons-material/AddPhotoAlternateOutlined";
import "swiper/css";
import "swiper/css/pagination";
import { Pagination } from "swiper/modules";
import ImageItem from "./ImageItem";
interface ImageBrowserProps {
  multiple?: boolean;
  onChange: (...event: any[]) => void;
  onBlur: () => void;
  value: string;
  disabled?: boolean | undefined;
  name: string;
  aspectRatio?: string;
}

const ImageBrowser = forwardRef<HTMLInputElement, ImageBrowserProps>(
  (
    { multiple, onChange, onBlur, aspectRatio = "1/1" }: ImageBrowserProps,
    ref
  ) => {
    const [open, setOpen] = useState(false);
    const [selectedFiles, setSelectedFile] = useState<FileMetadata[]>([]);

    const handleChangeFiles = (files: FileMetadata[]) => {
      setSelectedFile(files);
    };
    const handleSelect = () => {
      if (multiple) onChange(selectedFiles.map((file) => file.fileId));
      else onChange(selectedFiles[0].id);
      setOpen(false);
    };

    const handleRemove = useCallback(
      (id: string) => {
        setSelectedFile(selectedFiles.filter((file) => file.id !== id));
      },
      [setSelectedFile]
    );

    const handleCancel = useCallback(() => {
      setOpen(false);
      onBlur();
    }, [onBlur]);

    return (
      <>
        <Paper
          variant="outlined"
          sx={{
            aspectRatio: aspectRatio,
            backgroundRepeat: "no-repeat",
            backgroundSize: "cover",
            display: "grid",
            placeItems: "center",
            backgroundColor: (theme: Theme) => theme.palette.background.default,
            cursor: "pointer",
            padding: 1,
          }}
          onClick={() => setOpen(true)}>
          {selectedFiles.length ? (
            <>
              <Swiper
                pagination={true}
                modules={[Pagination]}
                slidesPerView={1}>
                {selectedFiles.map((image) => (
                  <SwiperSlide key={image.id}>
                    <ImageItem image={image} handleRemove={handleRemove} />
                  </SwiperSlide>
                ))}
              </Swiper>
            </>
          ) : (
            <Box sx={{ textAlign: "center" }}>
              <Typography>Click here to add more images.</Typography>
              <Typography>
                <AddPhotoAlternateOutlinedIcon />
              </Typography>
            </Box>
          )}
        </Paper>
        {open && (
          <Dialog
            open={open}
            disableEscapeKeyDown
            fullWidth={true}
            maxWidth="xl">
            <DialogContent sx={{ padding: 0 }}>
              <FileExplorer
                multiple={multiple}
                filter={FileType.IMAGE}
                onSelectFiles={handleChangeFiles}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleCancel} color="secondary">
                Cancel
              </Button>
              <Button
                onClick={handleSelect}
                color="primary"
                variant="contained"
                disabled={selectedFiles.length == 0}>
                Select
              </Button>
            </DialogActions>
          </Dialog>
        )}
      </>
    );
  }
);

export default ImageBrowser;
