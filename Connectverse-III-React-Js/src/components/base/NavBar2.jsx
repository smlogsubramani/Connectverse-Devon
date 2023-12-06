import React, { useState } from "react";
import { NavLink } from "react-router-dom";

import MenuIcon from "@mui/icons-material/Menu";
import CloseIcon from "@mui/icons-material/Close";

import { getUserId, handleLogout } from "../../utils/auth";

import "./NavBar2.css";

const NavBar2 = () => {
  const [click, setClick] = useState(false);
  const handleClick = () => setClick(!click);
  return (
    <div className="nav-container">
      <div className="navbar">
        <div className="navbar-logo">
          <h4 className="navbar-logo">ConnectVerse</h4>
        </div>

        <div className="menu-items">
          <div className="menu-icon" onClick={handleClick}>
            {click ? <CloseIcon /> : <MenuIcon />}
          </div>
          <div className="nav-hidden">
            <ul className={click ? "nav-menu active" : "navbar-items"}>
              <li className="navbar-item">
                <NavLink to="/">Home</NavLink>
              </li>
              <li className="navbar-item">
                <NavLink to="/projects">Projects</NavLink>
              </li>
              <li className="navbar-item">
                <NavLink to="/courses">Courses</NavLink>
              </li>
              <li className="navbar-item">
                <NavLink to="/jobs">Jobs</NavLink>
              </li>
              <div className="navbar-login">
                {
                  console.log(getUserId())
                }
                {
                  getUserId() ? <button className="navbar-btn" onClick={() => {handleLogout()}}>Log-Out</button> : <NavLink className="navbar-btn" to="/login">Login</NavLink>

                }
              </div>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NavBar2;
