import ArtistRequest, {AlbumType} from "@models/AlbumRequest";
import ObjectResponse from "@models/ObjectResponse";
import Artist from "@models/Artist";
import {
    Box,
    Breadcrumbs,
    Avatar,
    Card,
    CardContent,
    Checkbox,
    FormControlLabel,
    FormGroup,
    FormHelperText,
    FormLabel,
    Link,
    MenuItem,
    OutlinedInput,
    Paper,
    Select,
    Theme,
    Typography,
} from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import artistService from "@services/ArtistService";
import ArtistService from "@services/ArtistService";
import countryService from "@services/CountryService.ts";
import {enqueueSnackbar} from "notistack";
import React, {memo, useEffect, useState} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import ImageBrowser from "../../components/ImageBrowser";
import style from "./style";
import {LoadingButton} from "@mui/lab";
import SaveIcon from "@mui/icons-material/Save";
import AlbumRequest from "@models/AlbumRequest";
import albumService from "@services/AlbumService.ts";
import Genre from "@models/Genre.ts";
import genreService from "@services/GenreService.ts";
import Distributor from "@models/Distributor.ts";
import distributorService from "@services/DistributorService.ts";

const ArtistForm = memo(() => {
    const [loading, setLoading] = useState(true);
    const [artists, setArtists] = useState<Artist[]>([]);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [distributor, setDistributor] = useState<Distributor[]>([]);
    useEffect(() => {
        Promise.all([artistService.findAll(), genreService.findAll(), distributorService.findAll()])
            .then(([artists, genres, distributor]) => {
                setArtists(artists.data.content)
                setGenres(genres.data)
                setDistributor(distributor.data.content)
            })
            .finally(() => setLoading(false));
    }, []);

    const {
        handleSubmit,
        control,
        formState: {errors},
    } = useForm<AlbumRequest>({
        mode: "onSubmit",
        defaultValues: {
            isIndie: false,
            description: " ",
            artistIds: [],
            genreIds: [],
            trackIds: []
        },
    });
    const onSubmit: SubmitHandler<AlbumRequest> = (data) => {
        setLoading(true);
        console.log(data)
        albumService.create(data)
            .then((rs) => enqueueSnackbar(rs.message, {variant: "success"}))
            .catch((rs) => enqueueSnackbar(rs.message, {variant: "error"}))
            .finally(() => setLoading(false));
    };


    return (
        <Box component="form" onSubmit={handleSubmit(onSubmit)}>
            <Grid container spacing={2}>
                <Grid sm={12}>
                    <Paper
                        variant="outlined"
                        sx={{
                            padding: 2,
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "space-between",
                        }}>
                        <Breadcrumbs aria-label="breadcrumb">
                            <Link underline="hover" color="inherit" href="/">
                                Home
                            </Link>
                            <Link underline="hover" color="inherit" href="/artists">
                                Albums
                            </Link>
                            <Typography color="text.primary">Add</Typography>
                        </Breadcrumbs>
                        <LoadingButton
                            loading={loading}
                            loadingPosition="start"
                            startIcon={<SaveIcon/>}
                            variant="contained"
                            type="submit">
                            Save
                        </LoadingButton>
                    </Paper>
                </Grid>
                <Grid sm={8} container alignItems="flex-start">
                    <Grid sm={12}>
                        <Card variant="outlined" sx={style.paper}>
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Information
                                </Typography>
                            </Box>
                            <FormGroup sx={style.formGroup}>
                                <FormLabel sx={style.formLabel}>
                                    Album Name{" "}
                                    <Box
                                        component="span"
                                        sx={{color: (theme: Theme) => theme.palette.error.main}}>
                                        *
                                    </Box>
                                </FormLabel>
                                <Controller
                                    rules={{required: "Name must not be blank"}}
                                    name="name"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            error={!!errors.name}
                                            placeholder="Artist name"
                                        />
                                    )}
                                />
                                <FormHelperText error>{errors.name?.message}</FormHelperText>
                            </FormGroup>
                            <FormGroup sx={style.formGroup}>
                                <FormLabel sx={style.formLabel}>
                                    Alias{" "}
                                    <Box
                                        component="span"
                                        sx={{color: (theme: Theme) => theme.palette.error.main}}>
                                        *
                                    </Box>
                                </FormLabel>
                                <Controller
                                    rules={{required: "Alias must not be blank"}}
                                    name="alias"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            placeholder="Alias"
                                            error={!!errors.alias}
                                        />
                                    )}
                                />
                                <FormHelperText error>{errors.alias?.message}</FormHelperText>
                            </FormGroup>
                            <FormGroup sx={style.formGroup}>
                                <FormLabel sx={style.formLabel}>Release date</FormLabel>
                                <Controller
                                    rules={{required: "Birthday must not be blank"}}
                                    name="releaseDate"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            type="date"
                                            error={!!errors.releaseDate}
                                        />
                                    )}
                                />
                                <FormHelperText error>
                                    {errors.releaseDate?.message}
                                </FormHelperText>
                            </FormGroup>
                            <FormGroup sx={style.formGroup}>
                                <FormLabel sx={style.formLabel}>Description</FormLabel>

                                <Controller
                                    name="description"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            multiline
                                            placeholder="Description"
                                            rows={5}
                                        />
                                    )}></Controller>
                            </FormGroup>
                        </Card>
                    </Grid>
                    <Grid sm={12} sx={{alignItems: "start"}}>
                        <Card>
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Details
                                </Typography>
                            </Box>
                            <CardContent>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Album type</FormLabel>

                                    <Controller
                                        name="type"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : ''}
                                                placeholder="Artist Type"
                                                fullWidth
                                                input={<OutlinedInput error={!!errors.type}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Album type
                                                </MenuItem>
                                                <MenuItem value={AlbumType.EP} key={"EP"}>
                                                    EP
                                                </MenuItem>
                                                <MenuItem value={AlbumType.COMPILATION} key={AlbumType.COMPILATION}>
                                                    Compilation
                                                </MenuItem>
                                                <MenuItem value={AlbumType.LIVE} key={AlbumType.LIVE}>
                                                    Live
                                                </MenuItem>
                                                <MenuItem value={AlbumType.REMIX} key={AlbumType.REMIX}>
                                                    Remix
                                                </MenuItem>
                                                <MenuItem value={AlbumType.SINGLE} key={AlbumType.SINGLE}>
                                                    Single
                                                </MenuItem>
                                                <MenuItem value={AlbumType.STUDIO} key={AlbumType.STUDIO}>
                                                    Studio
                                                </MenuItem>
                                            </Select>
                                        )}
                                    />
                                    <FormHelperText error>{errors.type?.message}</FormHelperText>
                                </FormGroup>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Album type</FormLabel>

                                    <Controller
                                        name="genreIds"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : []}
                                                placeholder="Genre"
                                                fullWidth
                                                multiple
                                                input={<OutlinedInput error={!!errors.type}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Genres
                                                </MenuItem>
                                                {genres.map((genre) => <MenuItem value={genre.id}
                                                                                 key={genre.id}>{genre.name}</MenuItem>)}

                                            </Select>
                                        )}
                                    />
                                    <FormHelperText error>{errors.genreIds?.message}</FormHelperText>
                                </FormGroup>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Distributor</FormLabel>

                                    <Controller
                                        name="distributorId"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : ''}
                                                placeholder="Distributor"
                                                fullWidth
                                                input={<OutlinedInput error={!!errors.type}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Distributor
                                                </MenuItem>

                                                {distributor.map((d) => (
                                                    <MenuItem value={d.id} key={d.id}>
                                                        <Box display="flex" gap={1}>
                                                            <Avatar
                                                                alt="Remy Sharp"
                                                                src={d.thumbnail}
                                                                sx={{width: 28, height: 28}}
                                                            /> {d.name}
                                                        </Box></MenuItem>
                                                ))}

                                            </Select>
                                        )}
                                    />
                                    <FormHelperText error>{errors.type?.message}</FormHelperText>
                                </FormGroup>
                            </CardContent>
                        </Card>
                    </Grid>
                </Grid>
                <Grid sm={4} container alignContent="flex-start">
                    <Grid sm={12} sx={{alignItems: "start"}}>
                        <Card>
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Artists
                                </Typography>
                            </Box>
                            <CardContent>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Artists</FormLabel>

                                    <Controller
                                        name="artistIds"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : []}
                                                placeholder="Artist Type"
                                                fullWidth
                                                multiple

                                                input={<OutlinedInput error={!!errors.type}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Album type
                                                </MenuItem>
                                                {artists.map((artist) => (
                                                    <MenuItem value={artist.id} key={artist.id}>
                                                        <Box display="inline-flex" gap={1}>
                                                            <Avatar
                                                                alt="Remy Sharp"
                                                                src={artist.thumbnail}
                                                                sx={{width: 28, height: 28}}
                                                            /> {artist.name}
                                                        </Box>
                                                    </MenuItem>))}

                                            </Select>
                                        )}
                                    />
                                    <FormHelperText error>{errors.artistIds?.message}</FormHelperText>
                                </FormGroup>
                            </CardContent>
                        </Card>
                    </Grid>
                    <Grid sm={12}>
                        <Card variant="outlined">
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Image
                                </Typography>
                            </Box>

                            <CardContent>
                                <Controller
                                    name="imageId"
                                    control={control}
                                    rules={{required: "Avatar must not be blank"}}
                                    render={({field}) => <ImageBrowser {...field} />}
                                />
                                <FormHelperText error>
                                    {errors.imageId?.message}
                                </FormHelperText>
                            </CardContent>
                        </Card>
                    </Grid>

                    <Grid sm={12}>
                        <Card variant="outlined">
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Check
                                </Typography>
                            </Box>

                            <CardContent>
                                <Controller
                                    render={({field}) => (
                                        <FormControlLabel
                                            label="Indie"
                                            control={<Checkbox {...field} />}
                                        />
                                    )}
                                    name="isIndie"
                                    control={control}
                                />
                            </CardContent>
                        </Card>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    );
});

export default ArtistForm;
