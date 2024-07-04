import { ExplorerResponse } from "@models/ExplorerResponse";
import { Box, CircularProgress } from "@mui/material";
import React, { memo } from "react";
import style from "./style";
import Loader from "../Loader";
import InfiniteScroll from "react-infinite-scroll-component";
import { GridItem } from "./Item";

interface FileGridProps {
  data: ExplorerResponse;
  handleLoadMore: () => void;
  handleRefresh: () => void;
  loading: boolean;
  handleSelectAll: () => void;
  handleOpenContextMenu: (x: number, y: number) => void;
}

const FileGrid = ({
  data,
  handleLoadMore,
  loading,
  handleRefresh,
  handleOpenContextMenu,
}: FileGridProps) => {
  return (
    <Box position="relative" sx={style.contentContainer} id="grid-content">
      {loading ? (
        <Loader />
      ) : (
        <InfiniteScroll
          dataLength={data?.files.length | 0}
          hasMore={data?.hasMore}
          loader={<CircularProgress color="inherit" />}
          next={handleLoadMore}
          scrollableTarget="grid-content"
          refreshFunction={handleRefresh}
          style={{ overflow: "hidden" }}
          pullDownToRefresh>
          <Box sx={style.gridContent}>
            {data?.files.map((file) => (
              <GridItem
                file={file}
                key={file.id}
                handleOpenContextMenu={handleOpenContextMenu}
              />
            ))}
          </Box>
        </InfiniteScroll>
      )}
    </Box>
  );
};

export default memo(FileGrid);
