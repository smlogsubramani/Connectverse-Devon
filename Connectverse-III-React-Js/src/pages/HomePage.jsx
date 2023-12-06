import { useState, useEffect } from "react";

import JsonData from "../data";

import Header from "../components/home/Header";
import Features from "../components/home/Features";
import About from "../components/home/About";
import Services from "../components/home/Services";
import Gallery from "../components/home/Gallery";
import Testimonials from "../components/home/Testimonials";
import Team from "../components/home/Team";
import Contact from "../components/home/Contact";
import SmoothScroll from "smooth-scroll";

import "../styles/pages/home.css";

export const scroll = new SmoothScroll('a[href*="#"]', {
  speed: 1000,
  speedAsDuration: true,
});

const App = () => {
  const [landingPageData, setLandingPageData] = useState({});

  useEffect(() => {
    document.title = "Connectverse";

    setLandingPageData(JsonData);
  }, []);

  return (
    <div>
      <Header />
      <Features />
      <About />
      <Services />
      <Gallery />
      <Testimonials />
      <Team />
      <Contact />
    </div>
  );
};

export default App;
