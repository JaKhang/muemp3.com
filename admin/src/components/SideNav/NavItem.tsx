import {Link, ListItemButton, ListItemIcon, ListItemText} from "@mui/material";
import {clsx} from "clsx";
import {NavLink, useLocation} from "react-router-dom";
import {RouteItem} from "../../routes/RouteItem.ts";


export interface NavItemProps {
    route: RouteItem
    isSlim?: boolean
}


const NavItem = (props: NavItemProps) => {
    const {route} = props;
    const {pathname} = useLocation();
    const activeKey = pathname.split("/").slice(1)[route.level];
    const active = activeKey == route.key;





    const classes = clsx(
        "sidenav__item",
        active && "sidenav__item--active"
    )
    const InnerItem = () => {
        return (
            <ListItemButton className={classes}>
                {
                    route.icon && <ListItemIcon className="sidenav__item-icon">{route.icon}</ListItemIcon>
                }
                <ListItemText className="sidenav__item-text" primary={<span className="sidenav__item-text">{route.name}</span>}/>
            </ListItemButton>
        )
    }


    return (
        route.href ? (
            <Link href={route.href}>
               <InnerItem/>
            </Link>
        ) : (
            <NavLink to={route.path || "/"}>
                <InnerItem/>
            </NavLink>
        )
    );
};


export default NavItem;
