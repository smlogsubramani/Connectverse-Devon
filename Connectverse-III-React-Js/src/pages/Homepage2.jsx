import React, { useState, useEffect } from "react";
import { Container, Grid, Stack } from "@mui/material";
import { getUserId, getEmployeeId, getEmployerId } from "../utils/auth";
import Independence_day from "../assets/images/Independence_day.webp";
import EShram from "../assets/images/EShram.jpeg";
import Ministry from "../assets/images/Ministry.jpeg";
import MinistryOfLabour from "../assets/images/MinistryOfLabour.png";
import mainimg from "../assets/images/home-mainimg.jpg";
import aboutimg from "../assets/images/About.jpg";
import Features from "../components/home/Features";
import Services from "../components/home/Services";
import Contact from "../components/home/Contact";
import Stats from "./Stats";

import { getLanguage, setLanguage } from "../utils/app-settings";

import JsonData from "../data/Language.json";

import "../styles/pages/Homepage2.css";
import "../styles/pages/home.css";

const HomePage = () => {
  const [language, setLanguageSetting] = useState("English");
  const [data, setData] = useState(JsonData.Hindi);

  useEffect(() => {
    console.log(getEmployeeId)
    getLanguage() == "Hindi"
      ? setData(JsonData.Hindi)
      : getLanguage() == "Kanada"
      ? setData(JsonData.Kanada)
      : setData(JsonData.English);
  }, []);

  return (
    <div className="homepage">
      <div
        id="carouselExampleIndicators"
        class="carousel slide"
        data-ride="carousel"
      >
        <ol class="carousel-indicators">
          <li
            data-target="#carouselExampleIndicators"
            data-slide-to="0"
            class="active"
          ></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img class="d-block w-100" src={Ministry} alt="First slide" />
          </div>
          <div class="carousel-item">
            <img
              class="d-block w-100"
              src={MinistryOfLabour}
              alt="Second slide"
            />
          </div>
          <div class="carousel-item">
            <img class="d-block w-100" src={EShram} alt="Third slide" />
          </div>
        </div>
        <a
          class="carousel-control-prev"
          href="#carouselExampleIndicators"
          role="button"
          data-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a
          class="carousel-control-next"
          href="#carouselExampleIndicators"
          role="button"
          data-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
      <div className="homepage-container1">
        <Container>
          <Grid container>
            <Grid md={6} sm={12}>
              <div className="img-wrap">
                <img src={mainimg} alt="mainimg" className="mainimg" />
              </div>
            </Grid>
            <Grid md={6} sm={12}>
              <div className="maindesc-wrap">
                <h3 className="main-desc" >
                  {data.Header.text}
                </h3>
                <Stats stats={data.Header.stats} />
              </div>
            </Grid>
          </Grid>
        </Container>
      </div>

      <div className="homepage-container2">
        <Container>
          <Grid container spacing={3}>
            <Grid md={6} sm={12}>
              <div className="mission-container">
                <h2 className="mission-sub-heading">
                  {data.OUR_MISSION.tittle}
                </h2>
                <Stack spacing={3} marginLeft={2} sx={{ width: "350px" }}>
                  {data.OUR_MISSION.subTittles.map((title) => (
                    <p className="mission-desc">{title}</p>
                  ))}
                </Stack>
              </div>
            </Grid>
            <Grid md={6} sm={12}>
              <div className="vision-container">
                <h2 className="vision-sub-heading">{data.OUR_VISION.tittle}</h2>
                <Stack spacing={3} marginLeft={2} sx={{ width: "350px" }}>
                  {data.OUR_VISION.subTittles.map((title) => (
                    <p className="mission-desc">{title}</p>
                  ))}
                </Stack>
              </div>
            </Grid>
          </Grid>
        </Container>
      </div>
      <div className="features-wrap">
        <Features Features={data.Features} />
      </div>
      <div className="homepage-container3">
        <Container>
          <Grid container>
            <Grid md={6} sm={12}>
              <div className="about-container">
                <h3 className="sub-heading2">{data.About_Us.tittle}</h3>
                <Stack spacing={3} marginLeft={2} sx={{ width: "350px" }}>
                  {data.About_Us.subTittles.map((title) => (
                    <p className="aboutus-desc">{title}</p>
                  ))}
                </Stack>
              </div>
            </Grid>
            <Grid md={6} sm={12}>
              <img src={aboutimg} alt="aboutimg" className="aboutimg" />
            </Grid>
          </Grid>
        </Container>
      </div>
      <div className="services-wrap">
        <Services Services={data.Services} />
        <Contact contact={data.Contact} />
      </div>
    </div>
  );
};

export default HomePage;
