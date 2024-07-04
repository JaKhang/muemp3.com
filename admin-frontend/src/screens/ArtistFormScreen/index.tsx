import ArtistRequest from "@models/ArtistRequest";
import ObjectResponse from "@models/ObjectResponse";
import {
  Box,
  Breadcrumbs,
  Button,
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
import { enqueueSnackbar } from "notistack";
import React, { memo, useEffect, useState } from "react";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import ImageBrowser from "../../components/ImageBrowser";
import style from "./style";
import { LoadingButton } from "@mui/lab";
import SaveIcon from "@mui/icons-material/Save";

const ArtistForm = memo(() => {
  const [artistTypes, setArtistTypes] = useState<ObjectResponse[]>([]);
  const [countries, setCountries] = useState<ObjectResponse[]>([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    Promise.all([artistService.getTypes(), countryService.findAll()])
      .then(([typeRes, countriesRes]) => {
        setCountries(countriesRes.data);
        setArtistTypes(typeRes.data);
      })
      .finally(() => setLoading(false));
  }, []);
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm<ArtistRequest>({
    mode: "onSubmit",
    defaultValues: {
      isOfficial: true,
      isBand: false,
      isIndie: false,
      description: " ",
    },
  });
  const onSubmit: SubmitHandler<ArtistRequest> = (data) => {
    setLoading(true);
    artistService
      .createArtist(data)
      .then((rs) => enqueueSnackbar(rs.message, { variant: "success" }))
      .catch((rs) => enqueueSnackbar(rs.message, { variant: "error" }))
      .finally(() => setLoading(false));
  };

  console.log(errors);

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
                Artist
              </Link>
              <Typography color="text.primary">Add</Typography>
            </Breadcrumbs>
            <LoadingButton
              loading={loading}
              loadingPosition="start"
              startIcon={<SaveIcon />}
              variant="contained"
              type="submit">
              Save
            </LoadingButton>
          </Paper>
        </Grid>
        <Grid sm={8} container alignItems="flex-start">
          <Grid sm={12}>
            <Card variant="outlined" sx={style.paper}>
              <Box sx={{ padding: 2, borderBottom: "1px solid #eee" }}>
                <Typography
                  color={(theme: Theme) => theme.palette.text.secondary}
                  variant="body1">
                  Information
                </Typography>
              </Box>
              <FormGroup sx={style.formGroup}>
                <FormLabel sx={style.formLabel}>
                  Artist name{" "}
                  <Box
                    component="span"
                    sx={{ color: (theme: Theme) => theme.palette.error.main }}>
                    *
                  </Box>
                </FormLabel>
                <Controller
                  rules={{ required: "Name must not be blank" }}
                  name="name"
                  control={control}
                  render={({ field }) => (
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
                    sx={{ color: (theme: Theme) => theme.palette.error.main }}>
                    *
                  </Box>
                </FormLabel>
                <Controller
                  rules={{ required: "Alias must not be blank" }}
                  name="alias"
                  control={control}
                  render={({ field }) => (
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
                <FormLabel sx={style.formLabel}>Birthday</FormLabel>
                <Controller
                  rules={{ required: "Birthday must not be blank" }}
                  name="birthday"
                  control={control}
                  render={({ field }) => (
                    <OutlinedInput
                      {...field}
                      type="date"
                      error={!!errors.birthday}
                    />
                  )}
                />
                <FormHelperText error>
                  {errors.birthday?.message}
                </FormHelperText>
              </FormGroup>
              <FormGroup sx={style.formGroup}>
                <FormLabel sx={style.formLabel}>Artist type</FormLabel>

                <Controller
                  name="typeIds"
                  rules={{ required: "Type must select" }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      value={field.value ? field.value : []}
                      multiple
                      placeholder="Artist Type"
                      fullWidth
                      input={<OutlinedInput error={!!errors.typeIds} />}>
                      <MenuItem defaultChecked disabled value="">
                        Artist type
                      </MenuItem>
                      {artistTypes.map((type) => (
                        <MenuItem value={type.id} key={type.id}>
                          {type.name}
                        </MenuItem>
                      ))}
                    </Select>
                  )}
                />
                <FormHelperText error>{errors.typeIds?.message}</FormHelperText>
              </FormGroup>
              <FormGroup sx={style.formGroup}>
                <FormLabel sx={style.formLabel}>Country</FormLabel>

                <Controller
                  name="countryId"
                  rules={{ required: "Country must not be blank" }}
                  control={control}
                  render={({ field }) => (
                    <Select
                      {...field}
                      value={field.value}
                      placeholder="Artist Type"
                      fullWidth
                      input={<OutlinedInput error={!!errors.countryId} />}>
                      {countries.map((type) => (
                        <MenuItem value={type.id} key={type.id}>
                          {type.name}
                        </MenuItem>
                      ))}
                    </Select>
                  )}
                />
                <FormHelperText error>
                  {errors.countryId?.message}
                </FormHelperText>
              </FormGroup>
              <FormGroup sx={style.formGroup}>
                <FormLabel sx={style.formLabel}>Description</FormLabel>

                <Controller
                  name="description"
                  control={control}
                  render={({ field }) => (
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
          <Grid sm={12} sx={{ alignItems: "start" }}>
            <Card>
              <Box sx={{ padding: 2, borderBottom: "1px solid #eee" }}>
                <Typography
                  color={(theme: Theme) => theme.palette.text.secondary}
                  variant="body1">
                  Cover image
                </Typography>
              </Box>
              <CardContent>
                {/*------------------
                      Cover Image
                 --------------------*/}
                <Controller
                  control={control}
                  render={({ field }) => (
                    <ImageBrowser aspectRatio="5/2" {...field} />
                  )}
                  name="coverId"
                />
              </CardContent>
            </Card>
          </Grid>
        </Grid>
        <Grid sm={4} container alignContent="flex-start">
          <Grid sm={12}>
            <Card variant="outlined">
              <Box sx={{ padding: 2, borderBottom: "1px solid #eee" }}>
                <Typography
                  color={(theme: Theme) => theme.palette.text.secondary}
                  variant="body1">
                  Avatar
                </Typography>
              </Box>

              <CardContent>
                <Controller
                  name="avatarId"
                  control={control}
                  rules={{ required: "Avatar must not be blank" }}
                  render={({ field }) => <ImageBrowser {...field} />}
                />
                <FormHelperText error>
                  {errors.avatarId?.message}
                </FormHelperText>
              </CardContent>
            </Card>
          </Grid>

          <Grid sm={12}>
            <Card variant="outlined">
              <Box sx={{ padding: 2, borderBottom: "1px solid #eee" }}>
                <Typography
                  color={(theme: Theme) => theme.palette.text.secondary}
                  variant="body1">
                  Check
                </Typography>
              </Box>

              <CardContent>
                <Controller
                  render={({ field }) => (
                    <FormControlLabel
                      label="Band"
                      control={<Checkbox {...field} />}
                    />
                  )}
                  name="isBand"
                  control={control}
                />
                <Controller
                  render={({ field }) => (
                    <FormControlLabel
                      label="Official"
                      control={<Checkbox {...field} />}
                    />
                  )}
                  name="isOfficial"
                  control={control}
                />
                <Controller
                  render={({ field }) => (
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
