import React, {useState} from "react";
import ArtistList from "../../components/ArtistList";
import Grid from "@mui/material/Unstable_Grid2/Grid2";
import {
  Box,
  Breadcrumbs,
  Button,
  Link,
  Paper,
  Typography,
} from "@mui/material";

const ArtistListScreen = () => {



  return (
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
          <Box>
            <Breadcrumbs aria-label="breadcrumb">
              <Link underline="hover" color="inherit" href="/">
                Home
              </Link>
              <Typography color="text.primary">Artist</Typography>
            </Breadcrumbs>
          </Box>
          <Box>
            <Button>Add</Button>
          </Box>
        </Paper>
      </Grid>
      <Grid sm={12}>
        <Paper variant="outlined" sx={{ padding: 1 }}>
          <ArtistList />
        </Paper>
      </Grid>
    </Grid>
  );
};

export default ArtistListScreen;
