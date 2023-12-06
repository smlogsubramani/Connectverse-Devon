import React, { useState, useEffect } from "react";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import axios from "axios";

import { Container, Grid, Stack, Fab } from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import EditIcon from "@mui/icons-material/Edit";

import { getUserId, getToken } from "../utils/auth";

import Data from "../data";
import "../styles/pages/Profile2.css";

const INITIAL_STATE = {
  id: "",
  firstName: "",
  lastName: "",
  age: 0,
  gender: "",
  prefferedWorkType: "",
  cvUrl: "",
  email: "",
  batchNo: 0,
  mobileNumber: 0,
  address: "0",
  location: "0",
  state: null,
  readyToRelocate: null,
  hasDrivingLicence: null,
  hasVehicle: null,
  expectedWagePerHour: 0,
  expectedWorkingHoursPerWeek: 0,
  isTechnicalWorker: false,
  isOccupied: false,
  isBlocked: true,
  physicalHealthPoints: null,
  mentalHealthPoints: null,
  waitingForJobTime: null,
  inactiveJobSeekTime: null,
  jobReports: null,
  knowsToOperateMobile: null,
  knowsToReadAndWrite: null,
  isEmployer: false,
  creditPoints: null,
  hamlet: null,
  adminOfProject: {
    id: 0,
    tittle: "",
    subTittle: "Project Collaboration",
  },
  projects: [
    {
      id: 0,
      tittle: "0",
      subTittle: "Project Collaboration",
    },
  ],
  jobsApplied: [],
  job: null,
  company: null,
};

const Profile = () => {
  const { uid } = useParams();
  const navigate = useNavigate();

  const [user, setUser] = useState(INITIAL_STATE);
  const [isEmployer, setIsEmployer] = useState(false);
  const [loading, setLoading] = useState(false);

  const initializeUser = async () => {
    try {
      setLoading(true);
      const baseUrl = Data.AppSettings.baseUrl;
      const headers = { Authorization: getToken() };
      const response = await axios.get(`${baseUrl}/user/${uid}`, { headers });
      if (response.status == 200) {
        setUser(response.data);
        setIsEmployer(response.data.isEmployer);
      }
    } catch (e) {
      console.log(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    !getToken() && navigate("/login");
    initializeUser();
  }, []);

  return (
    <div className="profile-container">
      <Container>
        <div className="profile-desc">
          <h3 className="details">Personal Details</h3>
          <PersonIcon sx={{ color: "#050850", fontSize: "40px" }} />
        </div>

        <div className="edit-icon">
          <Fab size="small" aria-label="edit">
            <EditIcon color="#050850" />
          </Fab>
        </div>

        <div className="personal-details">
          <Grid container padding={3}>
            <Grid md={6} sm={12} xs={12}>
              <div className="details-left">
                <Stack spacing={2}>
                  <p>Firstname: {user.firstName}</p>
                  <p>Lastname: {user.lastName}</p>
                  <p>Age: {user.age}</p>
                  <p>Email: {user.email}</p>
                  <p>MobileNumber: {user.mobileNumber}</p>
                  <p>Gender: {user.gender}</p>
                  <p>Block: {user.address}</p>
                  <p>State: {user.state}</p>
                </Stack>
              </div>
            </Grid>
            <Grid md={6} sm={12} xs={12}>
              <div className="details-right">
                <Stack spacing={2}>
                  <p>Expected CTC/HR: {user.expectedWagePerHour}</p>
                  <p>WorkType: {user.prefferedWorkType}</p>
                  <p>
                    Ready to Relocate: {user.readyToRelocate ? "Yes" : "No"}
                  </p>
                  <p>
                    Driving License: {user.hasDrivingLicence ? "Yes" : "No"}
                  </p>
                  <p>Vehicle: {user.hasVehicle ? "Yes" : "No"}</p>
                  <p>Skilled Worker: {user.isTechnicalWorker ? "Yes" : "No"}</p>
                  <p>Already In a Job: {user.isOccupied ? "Yes" : "No"}</p>
                  <p>District: {user.location}</p>
                </Stack>
              </div>
            </Grid>
          </Grid>
        </div>
        <div className="other-details">
          <h3 className="other-desc">Other Details</h3>
          <Stack spacing={2}>
            <p>
              Project Working on as Admin:{" "}
              <NavLink to={`/project/${user.adminOfProject.id}`}>
                {user.adminOfProject.tittle}
              </NavLink>
            </p>
            <p>
              Projects Done:{" "}
              {user.projects.map((project) => (
                <NavLink to={`/project/${project.id}`}>
                  {project.tittle}
                </NavLink>
              ))}
            </p>
            <button className="cv-btn">
              <a href={user.cvUrl} target="_blank">
                Download Cv
              </a>
            </button>
          </Stack>
        </div>
      </Container>
    </div>
  );
};

export default Profile;
