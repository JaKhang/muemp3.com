import { Children } from "react";
import MenuItem, { MenuItemType } from "./MenuItem";
import AssignmentIndSharpIcon from "@mui/icons-material/AssignmentIndOutlined";
import LibraryMusicOutlinedIcon from "@mui/icons-material/LibraryMusicOutlined";
import ListAddOutlinedIcon from "@mui/icons-material/PlaylistAddOutlined";
import { SvgIcon } from "@mui/material";
import PlaylistAddOutlined from "@mui/icons-material/PlaylistAddOutlined";
import AlbumOutlinedIcon from "@mui/icons-material/AlbumOutlined";
import FormatListNumberedOutlinedIcon from "@mui/icons-material/FormatListNumberedOutlined";
export default {
  title: "Application",
  id: "dataManager",
  type: MenuItemType.GROUP,
  url: "",
  children: [
    {
      id: "artist",
      title: "Artist",
      type: MenuItemType.COLLAPSE,
      icon: AssignmentIndSharpIcon,
      children: [
        {
          id: "artist-form",
          title: "Add Artist",
          url: "/artists/form",
          type: MenuItemType.ITEM,
        },
        {
          id: "artist-list",
          title: "Artist list",
          url: "/artists/list",
          type: MenuItemType.ITEM,
        },
      ],
    },
    {
      id: "Album",
      title: "Album",
      type: MenuItemType.COLLAPSE,
      icon: LibraryMusicOutlinedIcon,
      children: [
        {
          id: "albums-form",
          title: "Add Albums",
          url: "/albums/form",
          type: MenuItemType.ITEM,
        },
        {
          id: "album-list",
          title: "Albums list",
          url: "/albums/list",
          type: MenuItemType.ITEM,
        },
      ],
    },
    {
      id: "playlist",
      title: "Playlist",
      type: MenuItemType.COLLAPSE,
      icon: PlaylistAddOutlined,
      url: "/playlists/",
      children: [
        {
          id: "playlist-form",
          title: "Playlist Form",
          url: "/playlists/form",
          type: MenuItemType.ITEM,
        },
        {
          id: "artist-list",
          title: "Artist List",
          url: "/playlists/list",
          type: MenuItemType.ITEM,
        },
      ],
    },
    {
      id: "track",
      title: "Tracks",
      type: MenuItemType.COLLAPSE,
      icon: AlbumOutlinedIcon,
      url: "/tracks/",
      children: [
        {
          id: "tracks-form",
          title: "Track From",
          url: "/tracks/form",
          type: MenuItemType.ITEM,
        },
        {
          id: "artist-list",
          title: "Track List",
          url: "/tracks/list",
          type: MenuItemType.ITEM,
        },
      ],
    },
    {
      id: "genre",
      title: "Genre",
      type: MenuItemType.COLLAPSE,
      icon: FormatListNumberedOutlinedIcon,
      url: "/genres/",
      children: [
        {
          id: "artist-form",
          title: "Add Genres",
          url: "/genres/form",
          type: MenuItemType.ITEM,
        },
        {
          id: "genres-list",
          title: "Genres list",
          url: "/genres/list",
          type: MenuItemType.ITEM,
        },
      ],
    },
  ],
} as MenuItem;
