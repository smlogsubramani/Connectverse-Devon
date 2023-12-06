import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Multiselect from "multiselect-react-dropdown";
import { message } from "antd";
import {
  Typography,
  Container,
  Box,
  Stack,
  Grid,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextareaAutosize
} from "@mui/material";

import { getEmployerId, getToken } from "../utils/auth";

import Data from "../data";

const INITIAL_JOB = {
  tittle: "",
  description: "",
  noOfDays: 0,
  workHoursPerDay: 8,
  payPerHour: 0,
  location: "",
  state: "",
  jobType: "",
  drivingLicenceRequired: false,
  vehicleWanted: false,
  requiredWorkers: 0,
  workStarted: false,
  isTechnicalJob: false,
  employerId: "",
  address: "",
  skillIds: [],
};

const INITIAL_SKILLS = [
  {
    id: 0,
    tittle: "",
    imageUrl: "",
  },
];

const JobCreation = () => {
  const navigate = useNavigate();

  const [states, setStates] = useState([]);
  const [workType, setWorkType] = useState([]);
  const [difficultyLevel, setDifficultyLevel] = useState([]);

  const [skills, setSkills] = useState(INITIAL_SKILLS);
  const [jobSkills, setJobSkills] = useState([]);
  const [requestBody, setRequestBody] = useState(INITIAL_JOB);
  const [loading, setLoading] = useState(false);

  const handleSelect = (projectSkillsArg, selectedItem) => {
    console.log(projectSkillsArg);
    setJobSkills(projectSkillsArg);
  };

  const handleRemove = (projectSkillsArg, removedItem) => {
    console.log(projectSkillsArg);
    setJobSkills(projectSkillsArg);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRequestBody((prev) => ({ ...prev, [name]: value }));
  };

  const initSkills = async () => {
    try {
      setLoading(true);
      const baseUrl = Data.AppSettings.baseUrl;
      const response = await axios.get(`${baseUrl}/skill/list`);
      if (response.status == 200) {
        setSkills(response.data);
        console.log(response.data);
      }
    } catch (e) {
      message.error("Uanble to Connect to Server!.");
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);
      const baseUrl = Data.AppSettings.baseUrl;
      const headers = { Authorization: getToken() };
      jobSkills.map((skill) => {
        console.log("ID", skill.id);
        requestBody.skillIds.push(skill.id);
        setRequestBody(requestBody);
      });
      requestBody.employerId = getEmployerId();
      console.log(requestBody);
      const response = await axios.post(`${baseUrl}/job`, requestBody, {
        headers,
      });
      if (response.status == 200) {
        navigate(`/profile/${getEmployerId()}`);
      }
    } catch (e) {
      message.error("Unable to create Prpject at this moment...");
    } finally {
      setLoading(false);
    }
  };

  const initOptions = async () => {
    try {
      setLoading(true);
      const baseUrl = Data.AppSettings.baseUrl;
      const url = `${baseUrl}/auth/states`;
      const resonse = await axios.get(url);
      if (resonse.status == 200) {
        setStates(resonse.data);
      }
    } catch (e) {
      console.log(e);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    (!getEmployerId()) && navigate("/login");
    initSkills();
    initOptions();
  }, [requestBody]);

  return (
    <Stack>
      <Box sx={{ padding: 4 }}>
        <Container>
          <Typography variant="h6" align="left" padding={1}>
            Job Creation Form
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container spacing={2}>
              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Title"
                    variant="outlined"
                    fullWidth
                    name="tittle"
                    value={requestBody.tittle}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Pay-per-hour"
                    variant="outlined"
                    fullWidth
                    name="payPerHour"
                    value={requestBody.payPerHour}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
              </Grid>

              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="No of Days"
                    variant="outlined"
                    fullWidth
                    name="noOfDays"
                    value={requestBody.noOfDays}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Work-hours"
                    variant="outlined"
                    fullWidth
                    name="workHoursPerDay"
                    value={requestBody.workHoursPerDay}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
              </Grid>
              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Location"
                    variant="outlined"
                    fullWidth
                    name="location"
                    value={requestBody.location}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="State"
                    variant="outlined"
                    fullWidth
                    name="state"
                    value={requestBody.state}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
              </Grid>
              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Job Type
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      label="Job Type"
                      name="jobType"
                      value={requestBody.jobType}
                      onChange={handleChange}
                      required
                    >
                      <MenuItem value="TECHNICAL">Technical</MenuItem>
                      <MenuItem value="HOUSEHOLD">House Hold</MenuItem>
                      <MenuItem value="CONSTRUCTION">Construction</MenuItem>
                      <MenuItem value="FARMING">Farming</MenuItem>
                      <MenuItem value="ANY">Any</MenuItem>
                      <MenuItem value="MIXED"> Other</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Required-workers"
                    variant="outlined"
                    fullWidth
                    name="requiredWorkers"
                    value={requestBody.requiredWorkers}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
              </Grid>

              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Driving License?
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      label="Driving-licence"
                      name="drivingLicenceRequired"
                      value={requestBody.drivingLicenceRequired}
                      onChange={handleChange}
                      required
                    >
                      <MenuItem value="true">Yes</MenuItem>
                      <MenuItem value="false">No</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Vehicle?
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      label="vehicle"
                      name="vehicleWanted"
                      value={requestBody.vehicleWanted}
                      onChange={handleChange}
                      required
                    >
                      <MenuItem value="true">Yes</MenuItem>
                      <MenuItem value="false">No</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
              </Grid>
              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Work Started
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      label="Work Already Started"
                      name="workStarted"
                      value={requestBody.workStarted}
                      onChange={handleChange}
                      required
                    >
                      <MenuItem value="true">Yes</MenuItem>
                      <MenuItem value="false">No</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12} md={6}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Technical Job
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      label="Technical Job"
                      name="isTechnicalJob"
                      value={requestBody.isTechnicalJob}
                      onChange={handleChange}
                      required
                    >
                      <MenuItem value="true">Yes</MenuItem>
                      <MenuItem value="false">No</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
              </Grid>
              <Grid xs={12} md={6} item>
                <Multiselect
                  placeholder="Select the skills required to work in this project"
                  style={{
                    width: "100%",
                    height: "15px"
                  }}
                  fullWidth
                  options={skills} // Options to display in the dropdown
                  selectedValues={jobSkills} // Preselected value to persist in dropdown
                  onSelect={handleSelect} // Function will trigger on select event
                  onRemove={handleRemove} // Function will trigger on remove event
                  displayValue="name" // Property name to display in the dropdown options
                />
              </Grid>
              <Grid xs={12} md={6} item>
                <TextField
                  id="outlined-basic"
                  label="Address"
                  variant="outlined"
                  fullWidth
                  name="address"
                  value={requestBody.address}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid container item>
                <InputLabel id="demo-simple-select-label">
                  Job Description
                </InputLabel>
                <TextareaAutosize
                  maxRows={6}
                  id="outlined-basic"
                  label="Job-Description"
                  variant="outlined"
                  fullWidth
                  name="description"
                  value={requestBody.description}
                  onChange={handleChange}
                  style={{ width: "100%", height: "100px", padding: "1%" }}
                  required
                />
              </Grid>
              <Grid container padding={2}>
                <Grid item spacing={3}>
                  <Button variant="contained" type="submit">
                    Submit
                  </Button>
                </Grid>
              </Grid>
            </Grid>
          </form>
        </Container>
      </Box>
    </Stack>
  );
};
export default JobCreation;

// address: "Thiruvannamalai"
// description: "This is an job posted by ministry of labour and development for renovation and maintenace of government buildings."
// drivingLicenceRequired: "false"
// employerId: "5bc99a74-23c7-447f-9215-8bec2b35f495"
// isTechnicalJob: false
// jobType: "CONSTRUCTION"
// location: "Cheyyar"
// noOfDays: "50"
// ocation: "h"
// payPerHour: "40"
// requiredWorkers: "10"
// skillIds: []
// state: "TamilNadu"
// tittle: "Government Building Renovation"
// vehicleWanted: false
// workHoursPerDay: 8
// workStarted: false
