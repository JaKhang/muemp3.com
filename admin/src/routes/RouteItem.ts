import {JSX} from "react";
import {RouteType} from "./RouteType.ts";

export  interface RouteItem{
    type: RouteType
    path?: string
    element: JSX.Element;
    name: string,
    key: string,
    noCollapse: boolean,
    icon?: JSX.Element,
    href?: string;
    subItems?: RouteItem[],
    level: number;
}