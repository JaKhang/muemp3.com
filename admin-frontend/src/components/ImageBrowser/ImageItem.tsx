import { FileMetadata } from "@models/FileSystemMetadata";
import { Box } from "@mui/material";
import React from "react";

interface ImageItemProp {
  image: FileMetadata;
  handleRemove: (imageId: string) => void;
}

const ImageItem = ({ image }: ImageItemProp) => {
  return (
    <Box
      sx={{
        display: "grid",
        placeItems: "center",
      }}>
          <img src={image.thumbnail} />
    </Box>
  );
};

export default ImageItem;
