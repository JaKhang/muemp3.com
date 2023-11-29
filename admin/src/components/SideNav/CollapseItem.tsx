import {ExpandLess} from "@mui/icons-material";
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import {Box, ButtonBase, Collapse, List, ListItemButton, ListItemIcon, ListItemText} from "@mui/material";
import {clsx} from "clsx";
import {memo, useState} from 'react';
import {useLocation} from "react-router-dom";
import {RouteItem} from "../../routes/RouteItem.ts";
import {RouteType} from "../../routes/RouteType.ts";
import NavItem from "./NavItem.tsx";
interface CollapseItemProps {
    route : RouteItem
}
const CollapseItem = ({route}:CollapseItemProps) => {
    const [open, setOpen] = useState();
    const {pathname} = useLocation();
    const activeKey = pathname.split("/").slice(1)[route.level];
    const active = activeKey == route.key;

    const classes = clsx(
        "sidenav-collapse",
        active && "sidenav-collapse--active"
    );

    console.log(open)

    return (
        <Box className={classes}>
            <Collapse in={open} timeout="auto"  collapsedSize={46}>
                <ListItemButton className='sidenav__item' onClick={() => setOpen(!open)}>
                    {
                        route.icon && <ListItemIcon className="sidenav__item-icon sidenav__collapse-icon">{route.icon}</ListItemIcon>

                    }
                    <ListItemText className="sidenav__item-text" primary={<span className="sidenav__item-text sidenav__collapse-text">{route.name}</span>}/>
                    {open ? <ExpandLess className="sidenav__expand-icon"/> : <ChevronRightIcon className="sidenav__expand-icon"/>}
                </ListItemButton>
                <List className='sidenav__list'>

                    {
                        route.subItems && route.subItems.map((item) => {
                            if(item.type == RouteType.COLLAPSE){
                                return <CollapseItem route={item} key={item.key}/>
                            } else {
                                return <NavItem route={item} key={item.key}/>
                            }
                        })
                    }
                </List>
            </Collapse>
        </Box>
    );
};

export default memo(CollapseItem);
