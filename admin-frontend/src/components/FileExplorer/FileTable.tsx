import {
  Box,
  Checkbox,
  CircularProgress,
  Table,
  TableBody,
  TableCell,
  TableHead,
} from "@mui/material";
import React, { memo } from "react";
import style from "./style";
import InfiniteScroll from "react-infinite-scroll-component";
import { ExplorerResponse } from "@models/ExplorerResponse";
import { TableItem } from "./Item";
import Loader from "../Loader";

interface FileTableProps {
  data: ExplorerResponse;
  handleLoadMore: () => void;
  handleRefresh: () => void;
  loading: boolean;
  handleSelectAll: () => void;
  isSelectAll: boolean;
  handleOpenContextMenu: (x: number, y: number) => void;
}

const FileTable = ({
  data,
  handleLoadMore,
  handleRefresh,
  handleSelectAll,
  handleOpenContextMenu,
  isSelectAll,
  loading,
}: FileTableProps) => {
  console.log("File Table");

  return (
    <Box sx={style.contentContainer} id="table-content">
      <InfiniteScroll
        dataLength={data?.files.length | 0}
        hasMore={data?.hasMore}
        loader={<CircularProgress color="inherit" />}
        next={handleLoadMore}
        refreshFunction={handleRefresh}
        style={{ overflow: "hidden" }}
        pullDownToRefresh>
        <Table size="small">
          <TableHead>
            <TableCell width={"5%"}>
              <Checkbox onChange={handleSelectAll} checked={isSelectAll} />
            </TableCell>
            <TableCell width={"2%"} sx={{ paddingX: 0 }}></TableCell>
            <TableCell>...</TableCell>
            <TableCell width={"2%"}></TableCell>
            <TableCell width={"20%"}>Modified Date</TableCell>
            <TableCell width={"10%"}>Size</TableCell>
          </TableHead>
          <TableBody>
            {!loading ? (
              data?.files.map((file) => (
                <TableItem
                  file={file}
                  key={file.id}
                  handleOpenContextMenu={handleOpenContextMenu}
                />
              ))
            ) : (
              <Loader />
            )}
          </TableBody>
        </Table>
      </InfiniteScroll>
    </Box>
  );
};

export default memo(FileTable, (prev, next) => prev.data === next.data);
