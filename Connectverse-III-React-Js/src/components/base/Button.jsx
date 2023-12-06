import React from "react";
import { NavLink } from "react-router-dom";

import { getUserId, handleLogout } from "../../utils/auth";

import "./Button.css";

export function Button({ Login, Logout }) {
  return (
    <div>
      {
        (getUserId() == null) ?
          <NavLink to="/login">
            <button style={{ backgroundColor: "#050850" }} className="btn">{ Login }</button>
          </NavLink> :
          <button style={{ backgroundColor: "#050850" }} className="btn" onClick={() => {handleLogout()}}>{ Logout }</button>
      }
    </div>
  );
}
