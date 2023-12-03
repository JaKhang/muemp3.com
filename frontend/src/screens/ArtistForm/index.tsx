import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import PersonAddAlt1Icon from '@mui/icons-material/PersonAddAlt1';
import PhotoIcon from '@mui/icons-material/Photo';
import LoadingButton from '@mui/lab/LoadingButton';
import {
    Checkbox,
    FormControl,
    FormControlLabel,
    InputLabel, ListItemText,
    MenuItem,
    OutlinedInput,
    Select, SelectChangeEvent,
    TextField
} from "@mui/material";
import Grid from '@mui/material/Unstable_Grid2';
import { useEffect, useState} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import {toast} from "react-toastify";
import ImageUploader from "../../components/ImageUploader";
import {FormMessage} from "../../constants";
import {ArtistRequest} from "../../models/Artist.ts";
import {ArtistType} from "../../models/ArtistType.ts";
import artistService from "../../services/ArtistService.ts";
import errorHandler from "../../services/ErorrHandler.ts";




const AristForm = () => {

    const [artistTypes, setArtistTypes] = useState<ArtistType[]>([])
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        artistService
            .getAllType()
            .then((response) => setArtistTypes(response.data))
    }, [])

    const {
        handleSubmit,
        control,
    } = useForm<ArtistRequest>({
        mode: "onBlur" && "all"
    })


    const onSubmit: SubmitHandler<ArtistRequest> = (data) => {
        toast.success("hello")
        // setLoading(true);
        // if(data.thumbnail){
        //     artistService
        //         .postWithImage(data.thumbnail, data)
        //         .then(rs => toast.success(`Thêm thành công nghệ sĩ ${rs.data.name}`))
        //         .catch(rs => errorHandler.handle(rs))
        //         .finally(() => setLoading(false))
        // } else {
        //     artistService
        //         .post(data)
        //         .then(rs => console.log(rs.data))
        //         .catch(rs => console.log(rs))
        //         .finally(() => setLoading(false))
        // }
    }

    const handleSelectChange = (event: SelectChangeEvent<string | never[]>, callback: any) => {
        const {
            target: { value },
        } = event;
        callback(typeof value === 'string' ? value.split(',') : value);
    };

    return (
        <form className="artist-form" onSubmit={handleSubmit(onSubmit)}>

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
                            <LoadingButton
                                type="submit"
                                variant="contained"
                                className="card__submit-btn"
                                loading={loading}
                                startIcon={<AddCircleOutlineIcon/>}>
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
                                                <Controller
                                                    render={({field}) => (
                                                        <ImageUploader onChange={e => field.onChange(e.target.files[0])}/>)}
                                                    name="thumbnail"
                                                    control={control}
                                                />
                                            </Grid>
                                            <Grid>
                                                <LoadingButton
                                                    variant="contained"
                                                    className="card__submit-btn"
                                                    startIcon={<PhotoIcon/>}>
                                                    Chọn ảnh có sẵn
                                                </LoadingButton>
                                            </Grid>
                                        </div>
                                    </div>
                                </div>
                            </Grid>
                            <Grid alignItems="stretch" md={7} xl={8} style={{display: "flex", alignContent: "stretch", justifyContent: "stretch"}}>
                                <div className='paper' style={{maxWidth: "100%"}}>
                                    <div className="paper__header">
                                        <div className="paper__header-title">
                                            Profile
                                        </div>
                                    </div>

                                    <div className="paper__body">
                                        <Grid container spacing={6}>
                                            <Grid md={12} lg={6}>
                                                <Controller
                                                    name="name"
                                                    defaultValue={''}
                                                    control={control}
                                                    rules={{required: FormMessage.REQUIRED}}
                                                    render={({
                                                                 field,
                                                                 fieldState: {error},
                                                             }) => (
                                                        <TextField
                                                            helperText={error ? error.message : null}
                                                            error={!!error}
                                                            fullWidth
                                                            label="Nghệ danh"
                                                            {...field}
                                                        />
                                                    )}
                                                />
                                            </Grid>

                                            <Grid md={12} lg={6}>
                                                <Controller
                                                    name="alias"
                                                    defaultValue={''}
                                                    control={control}
                                                    rules={{required: FormMessage.REQUIRED}}
                                                    render={({
                                                                 field,
                                                                 fieldState: {error},
                                                             }) => (
                                                        <TextField
                                                            helperText={error ? error.message : null}
                                                            error={!!error}
                                                            fullWidth
                                                            label="Alias"
                                                            {...field}
                                                        />
                                                    )}
                                                />
                                            </Grid>

                                            <Grid md={12} lg={6}>
                                                <Controller
                                                    defaultValue={''}
                                                    name='birthday'
                                                    control={control}
                                                    render={({field}) => (
                                                        <TextField
                                                            label={<span>Ngày sinh</span>}
                                                            helperText=""
                                                            type="date"
                                                            fullWidth
                                                            {...field}
                                                        />)}
                                                />
                                            </Grid>
                                            <Grid md={12} lg={6}>
                                                <Controller
                                                    defaultValue={''}
                                                    name='typeIds'
                                                    control={control}
                                                    render={({field, fieldState}) => (
                                                        <FormControl fullWidth>
                                                            <InputLabel id="demo-simple-select-label">Age</InputLabel>
                                                            <Select
                                                                labelId="demo-simple-select-label"
                                                                id="demo-simple-select"
                                                                label="Loại"
                                                                multiple
                                                                input={<OutlinedInput label="Loại"/>}
                                                                value={field.value || []}
                                                                onChange={(e) => handleSelectChange(e, field.onChange)}
                                                                MenuProps={{sx:{maxHeight : 240}}}
                                                                renderValue={(selected) => selected.map(s => artistTypes.find((type => type.id === s)).name).join(", ")}


                                                            >
                                                                {artistTypes.map((type) => (
                                                                    <MenuItem key={type.id} value={type.id}>
                                                                        <Checkbox checked={field.value.indexOf(type.id) > -1} />
                                                                        <ListItemText primary={type.name} />
                                                                    </MenuItem>
                                                                ))}
                                                            </Select>
                                                        </FormControl>
                                                    )}
                                                />
                                            </Grid>
                                            <Grid>

                                                <Controller
                                                    name='isOfficial'
                                                    control={control}
                                                    render={({field}) => (
                                                        <FormControlLabel
                                                            value="true"
                                                            control={<Checkbox {...field}/>}
                                                            label="Offical"
                                                        />
                                                    )}
                                                />
                                            </Grid>
                                            <Grid>
                                                <Controller
                                                    name='isBand'
                                                    control={control}
                                                    render={({field}) => (
                                                        <FormControlLabel
                                                            value="true"
                                                            control={<Checkbox {...field}/>}
                                                            label="Band"
                                                        />
                                                    )}
                                                />
                                            </Grid>
                                            <Grid>
                                                <div>
                                                    <FormControlLabel
                                                        value="true"
                                                        control={<Checkbox/>}
                                                        label="Indie"
                                                    />
                                                </div>
                                            </Grid>

                                            <Grid xs={12}>

                                                <Controller
                                                    name='description'
                                                    defaultValue={''}
                                                    control={control}
                                                    render={({field}) => (
                                                        <TextField
                                                            label="Mô tả"
                                                            helperText=""
                                                            fullWidth
                                                            multiline
                                                            maxRows={6}
                                                            rows={6}
                                                            {...field}

                                                        />
                                                    )}
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
    )
        ;
};

export default AristForm;
