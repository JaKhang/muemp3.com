import {Box, Divider, List} from "@mui/material";
import {clsx} from "clsx";
import {memo} from "react";
import logo from "../../assets/logo.png";
import {RouteItem} from "../../routes/RouteItem.ts";
import routes from "../../routes/routes.tsx";
import {RouteType} from "../../routes/RouteType.ts";
import CollapseItem from "./CollapseItem.tsx";
import NavItem from "./NavItem.tsx";

export interface SideNavProps {
    isSlim?: boolean

}


const SideNav = (props: SideNavProps) => {
    const {isSlim} = props;

    const classes = clsx(
        "sidenav",
        isSlim && "sidenav--slim"
    );

    const Item = ({route}: { route: RouteItem }) => {
        if (route.type == RouteType.COLLAPSE) {
            return <CollapseItem route={route} key={route.key}/>;
        } else {
            return <NavItem route={route} key={route.key}/>;
        }
    };

    return (
        <Box className={classes}>
            <Box className="sidenav__container">
                <Box className="sidenav__header">
                    <span className='sidenav__logo'>
                    <img className='sidenav__logo-image' src={logo}/>
                    <span className='sidenav__logo-text'>
                        Admin
                    </span>
                </span>
                </Box>
                <Divider/>
                <List
                    component="nav"
                    aria-labelledby="nested-list-subheader"
                    className="sidenav__list">
                    {routes.map((route) => (
                        <Item route={route}/>
                    ))}
                </List>
            </Box>
        </Box>
    );
};

export default memo(SideNav);
