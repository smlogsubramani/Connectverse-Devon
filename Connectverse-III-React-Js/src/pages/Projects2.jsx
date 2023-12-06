import React from "react";
import Card from "./Card";
import "./Projects2.css";
import { Container, Grid, Stack } from "@mui/material";
function Projects() {
  return (
    <Container>
      <div className="project-container">
        <p className="projects-desc">Projects</p>
        <Stack direction="row" spacing={3} marginLeft="36px">
          <div className="projects-status">
            <p>45</p>
            <p className="light">In Progress</p>
          </div>
          <div className="projects-status">
            <p>24</p>
            <p className="light">Upcoming</p>
          </div>
          <div className="projects-status">
            <p>12</p>
            <p className="light">Completed</p>
          </div>
        </Stack>
        <Grid container marginTop={4}>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          <Grid sm={12} md={3} marginBottom={2}>
            <Card />
          </Grid>
          {/* <Grid md={4} padding={3}>
            <Card />
          </Grid> */}
        </Grid>
      </div>
    </Container>
  );
}

export default Projects;
