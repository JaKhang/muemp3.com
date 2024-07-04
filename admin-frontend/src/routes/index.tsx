import RouteItem from "./RouteItem";
import FileExplorer from "../screens/FileExplorerScreen";
import ArtistForm from "../screens/ArtistFormScreen";
import ArtistListScreen from "../screens/ArtistListScreen";
import AlbumFrom from "../screens/AlbumFrom";
import TrackForm from "../screens/TrackForm";

export default [
  {
    path: "/file-explorer",
    element: <FileExplorer />,
  },
  {
    path: "/artists/form",
    element: <ArtistForm />,
  },
  {
    path: "/artists",
    element: <ArtistListScreen />,
  }
  ,
  {
    path: "/albums/form",
    element: <AlbumFrom />,
  },
  {
    path: "/tracks/form",
    element: <TrackForm />,
  }
] as RouteItem[];
