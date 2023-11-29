import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import LoadingButton from '@mui/lab/LoadingButton';
import {
    Button,
    TextField,
    FormControl,
    FormControlLabel,
    Checkbox,
    Select,
    InputLabel,
    MenuItem
} from "@mui/material";
import Grid from '@mui/material/Unstable_Grid2';
import ImageUploader from "../../components/ImageUploader";

const AristForm = () => {

    return (
        <form className="artist-form">

            <div className="card">
                <div className="card__container">
                    <div className="card__header">
                        <div className="card__header-item card__header-item--left">
                            <div className="card__header-icon">
                                <PersonAddAlt1Icon/>
                            </div>
                            <div className="card__header-title">
                                Thêm nghệ sĩ
                            </div>

                        </div>
                        <div className="card__header-item card__header-item--center">

                        </div>
                        <div className="card__header-item card__header-item--left">
                            <LoadingButton variant="contained" className="card__submit-btn" startIcon={<AddCircleOutlineIcon/>}>
                                Thêm
                            </LoadingButton>
                        </div>
                    </div>

                    <div className="card__body">
                        <Grid container spacing={6}>
                            <Grid md={5} xl={4}>
                                <div className="paper">
                                    <div className="paper__header">
                                        <div className="paper__header-title">
                                            Ảnh
                                        </div>
                                        <div className="paper__body">
                                            <Grid>
                                                <ImageUploader/>
                                            </Grid>
                                        </div>
                                    </div>
                                </div>
                            </Grid>
                            <Grid md={7} xl={8}>
                                <div className='paper'>
                                    <div className="paper__header">
                                        <div className="paper__header-title">
                                            Profile
                                        </div>
                                    </div>

                                    <div className="paper__body">
                                        <Grid container spacing={6}>
                                            <Grid md={12} lg={6}>
                                                <TextField
                                                    label={<span>Nghệ danh <span className="text--red">*</span></span>}
                                                    helperText=""
                                                    fullWidth
                                                />
                                            </Grid>
                                            <Grid md={12} lg={6}>
                                                <TextField
                                                    label={<span>Alias <span className="text--red">*</span></span>}
                                                    helperText=""
                                                    fullWidth


                                                />
                                            </Grid>

                                            <Grid md={12} lg={6}>
                                                <TextField
                                                    label="Ngày sinh"
                                                    helperText=""
                                                    fullWidth
                                                    type="date"

                                                />
                                            </Grid>
                                            <Grid md={12} lg={6}>
                                                <FormControl fullWidth>
                                                    <InputLabel>{<span>Vai trò <span className="text--red">*</span></span>}</InputLabel>
                                                    <Select
                                                        labelId="demo-simple-select-standard-label"
                                                        id="demo-simple-select-standard"
                                                        label="Age"
                                                    >
                                                        <MenuItem value="">
                                                            <em>None</em>
                                                        </MenuItem>
                                                        <MenuItem value={10}>Ten</MenuItem>
                                                        <MenuItem value={20}>Twenty</MenuItem>
                                                        <MenuItem value={30}>Thirty</MenuItem>
                                                    </Select>
                                                </FormControl>
                                            </Grid>
                                            <Grid>
                                                <div>
                                                    <FormControlLabel
                                                        value="true"
                                                        control={<Checkbox />}
                                                        label="Offical"
                                                    />
                                                </div>
                                            </Grid>

                                            <Grid>
                                                <div>
                                                    <FormControlLabel
                                                        value="true"
                                                        control={<Checkbox />}
                                                        label="Band"
                                                    />
                                                </div>
                                            </Grid>


                                            <Grid xs={12}>
                                                <TextField
                                                    label="Mô tả"
                                                    helperText=""
                                                    fullWidth
                                                    multiline
                                                    maxRows={6}
                                                    rows={6}
                                                    
                                                />
                                            </Grid>
                                        </Grid>
                                    </div>
                                </div>
                            </Grid>
                        </Grid>
                    </div>

                    <div className="card__footer">

                    </div>
                </div>
            </div>

        </form>
    );
};

export default AristForm;
