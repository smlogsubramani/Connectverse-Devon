import React from "react";
import "../styles/pages/Stats.css";
import { Grid, Container } from "@mui/material";

const Stats = ({ stats }) => {
  return (
    <div className="stats-container">
      <Container>
        <Grid container spacing={3} padding={3}>
          {
            stats.map(stat => (
              <Grid xs={6} padding={1}>
                <h3 className="stats-no">{ stat.value }</h3>
                <p className="stats-content">{ stat.tittle }</p>
              </Grid>
            ))
          }
          {/* <Grid xs={6} padding={1}>
            <h3 className="stats-no">640K</h3>
            <p className="stats-content">Villages in India</p>
          </Grid>
          <Grid xs={6} padding={1}>
            <h3 className="stats-no">8.06%</h3>
            <p className="stats-content">Unemployment Rate</p>
          </Grid>
          <Grid xs={6} padding={1}>
            <h3 className="stats-no">1380M</h3>
            <p className="stats-content">Populations in India</p>
          </Grid>
          <Grid xs={6} padding={1}>
            <h3 className="stats-no">111.2M</h3>
            <p className="stats-content">Unemployed people</p>
          </Grid> */}
        </Grid>
      </Container>
    </div>
  );
};
export default Stats;
