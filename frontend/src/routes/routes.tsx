import DashboardIcon from '@mui/icons-material/Dashboard';
import ArtistForm from "../screens/ArtistForm";
import DashBoard from "../screens/dashboard/DashBoard";
import SettingsSystemDaydreamIcon from '@mui/icons-material/SettingsSystemDaydream';
import FiberManualRecordIcon from '@mui/icons-material/FiberManualRecord';

import {RouteItem} from "./RouteItem.ts";
import {RouteType} from "./RouteType.ts";

const routes: RouteItem[]  = [
    {
        name: "Dashboard",
        icon: (<DashboardIcon/>),
        key: "dashboard",
        path: "/dashboard",
        element: (<DashBoard/>),
        noCollapse: true,
        type: RouteType.LINK,
        level: 0
    },
    {
        name: "Hệ thống",
        icon: (<SettingsSystemDaydreamIcon/>),
        key: "system",
        path: "/system",
        element: (<DashBoard/>),
        noCollapse: true,
        type: RouteType.LINK,
        level: 0

    },
    {
        name: "Nghệ sĩ",
        icon: (<DashboardIcon/>),
        key: "song",
        path: "/song",
        element: (<DashBoard/>),
        noCollapse: true,
        type: RouteType.COLLAPSE,
        level: 0,
        subItems: [
            {
                name: "Thêm bài hát",
                key: "form",
                path: "/artist/form",
                element: (<ArtistForm/>),
                noCollapse: true,
                type: RouteType.LINK,
                icon: <FiberManualRecordIcon  sx={{ fontSize: 8 }}/>,
                level: 1

            },
            {
                name: "Danh sách",
                key: "list",
                path: "/song/list",
                element: (<DashBoard/>),
                noCollapse: true,
                type: RouteType.LINK,
                icon: <FiberManualRecordIcon sx={{ fontSize: 8 }}/>,
                level: 1
            }
        ]
    }
];

export default routes


