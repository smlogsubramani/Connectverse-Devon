import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Menu, Space } from 'antd';

import { getUserId, handleLogout } from "../../utils/auth";
import { getLanguage, setLanguage } from "../../utils/app-settings";

import { Button } from "./Button";
import Language from "../../data/Language.json";

import "./NavBar.css";


const Navbar = () => {
  const [click, setClick] = useState(false);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

  const [langOpt, setLanguageOpt] = useState(0);
  const [data, setData] = useState(Language.Hindi);

  const languageOptions = [
    "English",
    "Hindi",
    "Kanada"
  ];

  const languageNav = [
    "हिन्दी",
    "ಕೆನಡಾ",
    "English"
  ];

  const changeLang = () => {
    (langOpt == 2) ? setLanguageOpt(0) : setLanguageOpt(langOpt + 1)
    console.log(languageOptions[langOpt]);
    setLanguage(languageOptions[langOpt+1]);
    window.location.reload();
  };

  useEffect(() => {
    if (getLanguage() == "Hindi") {
      setData(Language.Hindi);
      setLanguageOpt(1);
    } else if (getLanguage() == "Kanada") {
      setData(Language.Kanada);
      setLanguageOpt(2);
    } else {
      setData(Language.English);
      setLanguageOpt(0);
    }
  }, []);

  return (
    <>
      <nav className="navbar" sticky="top">
        <NavLink to="/" className="navbar-logo" onClick={closeMobileMenu}>
          Connectverse
        </NavLink>
        <div className="menu-icon" onClick={handleClick}>
          <i className={click ? "fas fa-times" : "fas fa-bars"} />
        </div>
        <ul className={click ? "nav-menu active" : "nav-menu"}>
          <li className="nav-item">
            <button className="nav-links" onClick={changeLang}>{languageNav[langOpt]}</button>
          </li>
          <li className="nav-item">
            <NavLink to="/" className="nav-links" onClick={closeMobileMenu}>
              {data.Navbar.Home}
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink to="/courses" className="nav-links" onClick={closeMobileMenu}>
              {data.Navbar.Courses}
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              to="/projects"
              className="nav-links"
              onClick={closeMobileMenu}
            >
              {data.Navbar.Projects}
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink to="/jobs" className="nav-links" onClick={closeMobileMenu}>
              {data.Navbar.Jobs}
            </NavLink>
          </li>
          <li>
            {
              (getUserId() != null) ?
                <NavLink
                  to="/login"
                  className="nav-links-mobile"
                  onClick={closeMobileMenu}
                >
                  {data.Navbar.Login}
                </NavLink> :
                <button
                  className="nav-links-mobile"
                  onClick={() => { closeMobileMenu(); handleLogout(); }}
                >
                  {data.Navbar.Logout}
                </button>
            }
          </li>
        </ul>
        <Button Login={data.Navbar.Login} Logout={data.Navbar.Logout} />
      </nav>
    </>
  );
}

export default Navbar;
