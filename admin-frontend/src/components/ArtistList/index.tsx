import Artist from "@models/Artist";
import InfiniteList from "@models/InfiniteList";
import { Delete, EditSharp } from "@mui/icons-material";
import {
  Avatar,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemSecondaryAction,
  ListItemText,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import ArtistService from "@services/ArtistService";
import React, { useEffect, useState } from "react";

interface ArtistListProps {}

const ArtistList = () => {
  const [artists, setArtists] = useState<InfiniteList<Artist>>({
    content: [],
    total: 0,
    hasMore: 0,
  });

  useEffect(() => {
    console.log("aaaa")
    ArtistService.findAll().then((rs) => setArtists(rs.data));
  }, []);

  return (
    <Table sx={{ width: "100%", bgcolor: "background.paper" }}>
      <TableHead>
        <TableCell width="10%"></TableCell>
        <TableCell align="left" width="10%"></TableCell>
        <TableCell>...</TableCell>
        <TableCell width="10%">Subs</TableCell>
        <TableCell width="10%">Song</TableCell>
        <TableCell width="20%"></TableCell>
      </TableHead>
      {artists.content.map((artist) => (
        <TableRow>
          <TableCell></TableCell>
          <TableCell>
            <Avatar src={artist.thumbnail} />
          </TableCell>
          <TableCell>{artist.name}</TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell>
            <IconButton>
              <EditSharp />
            </IconButton>
            <IconButton>
              <Delete />
            </IconButton>
          </TableCell>
        </TableRow>
      ))}
    </Table>
  );
};

export default ArtistList;
