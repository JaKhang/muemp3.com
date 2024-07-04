import {
    Box,
    Breadcrumbs,
    Card,
    CardContent,
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
    Avatar,
    FormControlLabel,
    Checkbox
} from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import artistService from "@services/ArtistService";
import {enqueueSnackbar} from "notistack";
import React, {memo, useEffect, useState} from "react";
import {Controller, SubmitHandler, useForm} from "react-hook-form";
import ImageBrowser from "../../components/ImageBrowser";
import style from "./style";
import {LoadingButton} from "@mui/lab";
import SaveIcon from "@mui/icons-material/Save";
import Artist from "@models/Artist.ts";
import Genre from "@models/Genre.ts";
import Distributor from "@models/Distributor.ts";
import genreService from "@services/GenreService.ts";
import distributorService from "@services/DistributorService.ts";
import AudioBrowser from "../../components/AudioBrowser";
import TrackRequest from "@models/TrackRequest.ts";
import trackService from "@services/TrackService.ts";
import Album from "@services/Album.ts";
import albumService from "@services/AlbumService.ts";

const TrackForm = memo(() => {
    const [loading, setLoading] = useState(true);
    const [artists, setArtists] = useState<Artist[]>([]);
    const [genres, setGenres] = useState<Genre[]>([]);
    const [distributor, setDistributor] = useState<Distributor[]>([]);
    const [albums, setAlbums] = useState<Album[]>([]);
    useEffect(() => {
        Promise.all([artistService.findAll(), genreService.findAll(), distributorService.findAll(), albumService.findAll()])
            .then(([artists, genres, distributor, albums]) => {
                setArtists(artists.data.content)
                setGenres(genres.data)
                setDistributor(distributor.data.content)
                setAlbums(albums.data.content)
            })
            .finally(() => setLoading(false));
    }, []);

    const {
        handleSubmit,
        control,
        formState: {errors},
    } = useForm<TrackRequest>({
        mode: "onSubmit",
        defaultValues: {
            isIndie: false,
            description: " ",
            artistIds: [],
            genreIds: [],
            process: false,
            isOfficial: true,
            status: "NORMAL",
            lyrics: ""
        },
    });
    const onSubmit: SubmitHandler<TrackRequest> = (data) => {
        setLoading(true);
        console.log(data)
        trackService.create(data)
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
                                Tracks
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
                                            placeholder="Track name"
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
                                <FormLabel sx={style.formLabel}>
                                    Index{" "}
                                    <Box
                                        component="span"
                                        sx={{color: (theme: Theme) => theme.palette.error.main}}>
                                        *
                                    </Box>
                                </FormLabel>
                                <Controller
                                    rules={{required: "Alias must not be blank"}}
                                    name="trackIndex"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            placeholder="Index"
                                            error={!!errors.alias}
                                            type="number"

                                        />
                                    )}
                                />
                                <FormHelperText error>{errors.alias?.message}</FormHelperText>
                            </FormGroup>
                            <FormGroup sx={style.formGroup}>
                                <FormLabel sx={style.formLabel}>
                                    Duration{" "}
                                    <Box
                                        component="span"
                                        sx={{color: (theme: Theme) => theme.palette.error.main}}>
                                        *
                                    </Box>
                                </FormLabel>
                                <Controller
                                    rules={{required: "Alias must not be blank"}}
                                    name="duration"
                                    control={control}
                                    render={({field}) => (
                                        <OutlinedInput
                                            {...field}
                                            placeholder="Duration"
                                            error={!!errors.alias}
                                            type="number"

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
                        <Card variant="outlined"    >
                            <Box sx={{padding: 2, borderBottom: "1px solid #eee"}}>
                                <Typography
                                    color={(theme: Theme) => theme.palette.text.secondary}
                                    variant="body1">
                                    Details
                                </Typography>
                            </Box>
                            <CardContent>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Album</FormLabel>

                                    <Controller
                                        name="albumId"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : []}
                                                placeholder="Genre"
                                                fullWidth
                                                input={<OutlinedInput error={!!errors.genreIds}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Album
                                                </MenuItem>
                                                {albums.map((album) => <MenuItem value={album.id}
                                                                                 key={album.id}>{album.name}</MenuItem>)}

                                            </Select>
                                        )}
                                    />
                                    <FormHelperText error>{errors.genreIds?.message}</FormHelperText>
                                </FormGroup>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Genre</FormLabel>

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
                                                input={<OutlinedInput error={!!errors.genreIds}/>}>
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
                                                input={<OutlinedInput error={!!errors.distributorId}/>}>
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
                                    <FormHelperText error>{errors.distributorId?.message}</FormHelperText>
                                </FormGroup>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>MV</FormLabel>
                                    <Controller
                                        name="mvLink"
                                        control={control}
                                        render={({field}) => (
                                            <OutlinedInput
                                                {...field}
                                                multiline
                                                placeholder="Lyric"
                                            />
                                        )}></Controller>
                                </FormGroup>
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Description</FormLabel>
                                    <Controller
                                        name="lyrics"
                                        control={control}
                                        render={({field}) => (
                                            <OutlinedInput
                                                {...field}
                                                multiline
                                                placeholder="Lyric"
                                                rows={5}
                                            />
                                        )}></Controller>
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
                                    Audio
                                </Typography>
                            </Box>

                            <CardContent>
                                <Controller
                                    name="audioId"
                                    control={control}
                                    rules={{required: "Audio must not be blank"}}
                                    render={({field}) => <AudioBrowser {...field} />}
                                />
                                <FormHelperText error>
                                    {errors.imageId?.message}
                                </FormHelperText>
                                <Controller
                                    render={({field}) => (
                                        <FormControlLabel
                                            label="pr"
                                            control={<Checkbox {...field} />}
                                        />
                                    )}
                                    name="process"
                                    control={control}
                                />
                            </CardContent>
                        </Card>
                    </Grid>

                </Grid>
                <Grid sm={4} container alignContent="flex-start">
                    <Grid sm={12} sx={{alignItems: "start"}}>
                        <Card variant="outlined">
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
                                                placeholder="Artists"
                                                fullWidth
                                                multiple

                                                input={<OutlinedInput error={!!errors.artistIds}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Artist
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
                                <FormGroup sx={style.formGroup}>
                                    <FormLabel sx={style.formLabel}>Composer</FormLabel>
                                    <Controller
                                        name="composerId"
                                        rules={{required: "Type must select"}}
                                        control={control}
                                        render={({field}) => (
                                            <Select
                                                {...field}
                                                value={field.value ? field.value : ""}
                                                placeholder="Artists"
                                                fullWidth
                                                input={<OutlinedInput error={!!errors.artistIds}/>}>
                                                <MenuItem defaultChecked disabled value="">
                                                    Composer
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
                                    rules={{required: "avatar file require"}}
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
                                <Controller
                                    render={({field}) => (
                                        <FormControlLabel
                                            label="Is Official"
                                            control={<Checkbox {...field} />}
                                        />
                                    )}
                                    name="isOfficial"
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

export default TrackForm;
