import {Logout, PersonAdd, Settings} from "@mui/icons-material";
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AppsIcon from '@mui/icons-material/Apps';
import NotificationsIcon from '@mui/icons-material/Notifications';
import {
    Avatar,
    Badge,
    Box, Button, ButtonGroup,
    Divider,
    IconButton,
    ListItemIcon,
    Menu,
    MenuItem,
    TextField,
    Tooltip,
} from "@mui/material";
import {useState} from "react";
import {useLocation} from "react-router-dom";
import {useLayoutAction} from "../../redux/action.ts";
import {useAppDispatch} from "../../redux/store.ts";
import logo from '../../assets/logo.png'
import NavSearch from "./NavSearch.tsx";


const Navbar = () => {

    /*------------------
          Redux
    --------------------*/
    const dispatch = useAppDispatch();
    const {setSlim} = useLayoutAction();


    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

    const open = Boolean(anchorEl);


    /*------------------
          path
    --------------------*/


    const handleClick = (event: any) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };


    return (
        <Box
            component="nav"
            className='navbar'
        >
            <div className='navbar__group navbar__left'>
                <IconButton onClick={() => dispatch(setSlim())}>
                    <AppsIcon/>
                </IconButton>
            </div>
            <div className='navbar__group navbar__center'>
                <NavSearch/>
            </div>
            <div className='navbar__group navbar__right'>
                <IconButton>
                    <Badge badgeContent={4} color="primary">
                        <NotificationsIcon/>
                    </Badge>
                </IconButton>


                <Tooltip title="Account settings">
                    <IconButton
                        onClick={handleClick}
                        size="small"
                        sx={{ml: 2}}
                        aria-controls={open ? 'account-menu' : undefined}
                        aria-haspopup="true"
                        aria-expanded={open ? 'true' : undefined}
                    >
                        <Avatar sx={{width: 32, height: 32}}>M</Avatar>
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
                            overflow: 'visible',
                            filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                            mt: 1.5,
                            '& .MuiAvatar-root': {
                                width: 32,
                                height: 32,
                                ml: -0.5,
                                mr: 1,
                            },
                            '&:before': {
                                content: '""',
                                display: 'block',
                                position: 'absolute',
                                top: 0,
                                right: 14,
                                width: 10,
                                height: 10,
                                bgcolor: 'background.paper',
                                transform: 'translateY(-50%) rotate(45deg)',
                                zIndex: 0,
                            },
                        },
                    }}
                    transformOrigin={{horizontal: 'right', vertical: 'top'}}
                    anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
                >
                    <MenuItem onClick={handleClose}>
                        <ListItemIcon>
                            <AccountCircleIcon fontSize="small"/>
                        </ListItemIcon>
                        Profile
                    </MenuItem>
                    <MenuItem onClick={handleClose}>
                        <Avatar/> My account
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
                            <Settings fontSize="small"/>
                        </ListItemIcon>
                        Settings
                    </MenuItem>
                    <MenuItem onClick={handleClose}>
                        <ListItemIcon>
                            <Logout fontSize="small"/>
                        </ListItemIcon>
                        Logout
                    </MenuItem>
                </Menu>


            </div>
        </Box>
    );
};

export default Navbar;
