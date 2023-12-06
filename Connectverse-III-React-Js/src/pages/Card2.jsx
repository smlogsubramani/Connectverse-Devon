import React from "react";
import { Container, Stack } from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import Reactimg from "../assets/images/react.png";
import "../styles/pages/Card2.css";
function Card() {
  return (
    <Container>
      <div className="card-container">
        <Stack spacing={2}>
          <div className="project-img-wrappper">
            <img src={Reactimg} alt="java" className="Project-img" />
          </div>
          <p className="Date">March 20 2020</p>
          <p className="ProjectName">Hotel Management Website</p>
          <p className="Project-short-desc">
            This is a complete website which involves React and php
          </p>
          <div className="navigator">
            <ArrowForwardIosIcon />
            <a href="/" className="navigator-desc">
              Read More
            </a>
          </div>
        </Stack>
      </div>
    </Container>
  );
}

export default Card;
