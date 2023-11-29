import SearchIcon from '@mui/icons-material/Search';
import {ButtonBase, TextField} from "@mui/material";

const NavSearch = () => {
    return (
        <div className='nav-search'>
            <ButtonBase className='nav-search__button'>
                <SearchIcon/>
            </ButtonBase>
            { // @ts-ignore}
                <TextField className='nav-search__button' size='small' placeholder="Serach..."/>
            }
        </div>

    );
};

export default NavSearch;
