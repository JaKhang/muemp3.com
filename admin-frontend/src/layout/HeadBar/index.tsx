import {
    Avatar,
    Badge,
    Divider,
    FormControl,
    Icon,
    IconButton,
    ListItemIcon,
    Menu,
    MenuItem,
    OutlinedInput,
    Tooltip,
} from "@mui/material";
import MenuOpenSharpIcon from "@mui/icons-material/MenuOpenSharp";
import MenuCloseSharpIcon from "@mui/icons-material/MenuSharp";
import SettingsSharpIcon from "@mui/icons-material/SettingsSharp";
import NotificationsSharpIcon from "@mui/icons-material/NotificationsSharp";
import PersonAdd from "@mui/icons-material/PersonAdd";
import Logout from "@mui/icons-material/Logout";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import SearchSharpIcon from "@mui/icons-material/SearchSharp";
import FormatIndentDecreaseOutlinedIcon from "@mui/icons-material/FormatIndentDecreaseOutlined";
import FormatIndentIncreaseOutlinedIcon from "@mui/icons-material/FormatIndentIncreaseOutlined";
import React, {useState} from "react";
import "./style.scss";
import {useAuthAction, useLayoutAction} from "../../redux/action";
import {useAppDispatch} from "@redux/store";
import {useLayoutSelector} from "../../redux/selector";
import useAuthSelector from "@features/auth/useAuthSelector";

const HeadBar = () => {
    const dispatch = useAppDispatch();
    const {setSlim} = useLayoutAction();
    const {slim} = useLayoutSelector();
    const {principle} = useAuthSelector();
    const {logout} = useAuthAction();

    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    function handleSidebarSlim(event: React.MouseEvent): void {
        console.log("Click");

        dispatch(setSlim(!slim));
    }

    function handleLogout() {
        dispatch(logout());
        location.replace("/login");
    }

    return (
        <header className="head-bar__wrapper">
            <div className="head-bar__inner">
                <div className="head-bar__group">
                    <IconButton onClick={handleSidebarSlim}>
                        {!slim ? (
                            <FormatIndentDecreaseOutlinedIcon sx={{fontSize: 16}}/>
                        ) : (
                            <FormatIndentIncreaseOutlinedIcon sx={{fontSize: 16}}/>
                        )}
                    </IconButton>
                    <FormControl
                        sx={{
                            position: "relative",
                        }}>
                        <SearchSharpIcon
                            sx={{
                                position: "absolute",
                                fontSize: 16,
                                top: "50%",
                                left: "12px",
                                transform: "translateY(-50%)",
                                color: "var(--mui-palette-text-disabled)",
                            }}
                        />
                        <OutlinedInput
                            size="small"
                            placeholder="ctrl + k"
                            fullWidth
                            sx={{paddingLeft: 3}}
                        />
                    </FormControl>
                </div>
                <div className="head-bar__group">
                    <IconButton>
                        <Badge color="primary" badgeContent={99}>
                            <NotificationsSharpIcon sx={{fontSize: 16}}/>
                        </Badge>{" "}
                    </IconButton>
                    <IconButton>
                        <SettingsSharpIcon sx={{fontSize: 16}}/>
                    </IconButton>

                    <Tooltip title="Account settings">
                        <IconButton
                            onClick={handleClick}
                            size="small"
                            sx={{ml: 1}}
                            aria-controls={open ? "account-menu" : undefined}
                            aria-haspopup="true"
                            aria-expanded={open ? "true" : undefined}>
                            <Avatar
                                sx={{width: 36, height: 36}}
                                src={principle?.avatar}></Avatar>
                        </IconButton>
                    </Tooltip>

                    <Menu
                        anchorEl={anchorEl}
                        id="account-menu"
                        open={open}
                        onClose={handleClose}
                        onClick={handleClose}
                        PaperProps={{
                            elevation: 0,
                            sx: {
                                overflow: "visible",
                                filter: "drop-shadow(0px 2px 8px rgba(0,0,0,0.32))",
                                mt: 1.5,
                                "& .MuiAvatar-root": {
                                    width: 32,
                                    height: 32,
                                    ml: -0.5,
                                    mr: 1,
                                },
                                "&:before": {
                                    content: '""',
                                    display: "block",
                                    position: "absolute",
                                    top: 0,
                                    right: 14,
                                    width: 10,
                                    height: 10,
                                    bgcolor: "background.paper",
                                    transform: "translateY(-50%) rotate(45deg)",
                                    zIndex: 0,
                                },
                            },
                        }}
                        transformOrigin={{horizontal: "right", vertical: "top"}}
                        anchorOrigin={{horizontal: "right", vertical: "bottom"}}>
                        <MenuItem onClick={handleClose}>
                            <ListItemIcon>
                                <AccountCircleIcon fontSize="small"/>
                            </ListItemIcon>
                            Profile
                        </MenuItem>
                        <Divider/>
                        <MenuItem onClick={handleClose}>
                            <ListItemIcon>
                                <PersonAdd fontSize="small"/>
                            </ListItemIcon>
                            Add another account
                        </MenuItem>
                        <MenuItem onClick={handleClose}>
                            <ListItemIcon>
                                <SettingsSharpIcon fontSize="small"/>
                            </ListItemIcon>
                            Settings
                        </MenuItem>
                        <MenuItem onClick={handleLogout}>
                            <ListItemIcon>
                                <Logout fontSize="small"/>
                            </ListItemIcon>
                            Logout
                        </MenuItem>
                    </Menu>
                </div>
            </div>
        </header>
    );
};

export default HeadBar;
