import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { message } from "antd";

import {
  Typography,
  Container,
  Box,
  Stack,
  Grid,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Button,
} from "@mui/material";

import { getUserId } from "../utils/auth";
import Data from "../data";

const INITIAL_EMPLOYER = {
  id: "",
  gstNumber: "",
  employerUpdated: {
    firstName: "",
    lastName: "",
    age: 0,
    aadharId: "",
    gender: "",
    email: "",
    mobileNumber: 0,
    address: "",
    location: "",
  },
};

const INITIAL_USER = {
  firstName: "",
  lastName: "",
  age: 0,
  aadharId: "",
  gender: "",
  email: "",
  mobileNumber: 0,
  address: "",
  location: "",
};

export default function EmployerReg() {
  const navigate = useNavigate();

  const [requestBody, setRequestBody] = useState(INITIAL_EMPLOYER);
  const [employer, setEmployer] = useState(INITIAL_USER);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const [gender, setGender] = React.useState("");

  const handleChange2 = (event) => {
    setGender(event.target.value);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRequestBody((prev) => ({ ...prev, [name]: value }));
  };

  const handleChange1 = (e) => {
    const { name, value } = e.target;
    setEmployer((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitted...");

    const baseUrl = Data.AppSettings.baseUrl;

    try {
      setLoading(true);
      setError("");

      const url = `${baseUrl}/company`;

      console.log(requestBody);

      requestBody.employerUpdated = employer;

      console.log(requestBody);
      const response = await axios.post(url, requestBody);
      if (response.status == 200) {
        navigate("/login");
      }
    } catch (e) {
      setError(e);
      console.log(e);
      message.error("Unable To Login at this Moment...");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    (getUserId() && navigate("/"));
  }, [requestBody]);

  return (
    <Stack alignItems="center">
      <Box boxShadow={2}>
        <Container>
          <Typography variant="h5" sx={{ marginTop: "10px" }}>
            Employer registeration form
          </Typography>
          <form onSubmit={handleSubmit}>
            <Grid container paddingTop={2}>
              <Grid xs={12} md={6} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="FirstName"
                  variant="outlined"
                  fullWidth
                  name="firstName"
                  value={employer.firstName}
                  onChange={handleChange1}
                />
              </Grid>
              <Grid xs={12} md={6} spacing={3} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="LastName"
                  variant="outlined"
                  fullWidth
                  name="lastName"
                  value={employer.lastName}
                  onChange={handleChange1}
                />
              </Grid>
            </Grid>
            <Grid container paddingTop={2}>
              <Grid xs={12} md={6} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="Age"
                  variant="outlined"
                  fullWidth
                  name="age"
                  value={employer.age}
                  onChange={handleChange1}
                />
              </Grid>
              <Grid xs={12} md={6} spacing={3} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="Aadhar No."
                  variant="outlined"
                  fullWidth
                  name="aadharId"
                  value={employer.aadharId}
                  onChange={handleChange1}
                />
              </Grid>
            </Grid>
            <Grid container paddingTop={2}>
              <Grid xs={12} md={6} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="Email"
                  variant="outlined"
                  fullWidth
                  name="email"
                  value={employer.email}
                  onChange={handleChange1}
                />
              </Grid>
              <Grid xs={12} md={6} spacing={3} padding={1}>
                <TextField
                  id="outlined-basic"
                  label="Phone No."
                  variant="outlined"
                  fullWidth
                  name="mobileNumber"
                  value={employer.mobileNumber}
                  onChange={handleChange1}
                />
              </Grid>
            </Grid>
            <Grid xs={12} spacing={3} padding={1}>
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Gender</InputLabel>
                <Select
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  label="Gender"
                  name="gender"
                  value={employer.gender}
                  onChange={handleChange1}
                >
                  <MenuItem value="MALE">Male</MenuItem>
                  <MenuItem value="FEMALE">Female</MenuItem>
                  <MenuItem value="TRANSGENDER">Others</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item paddingTop={2} padding={1}>
              <TextField
                id="outlined-basic"
                label="Company-id"
                variant="outlined"
                fullWidth
                name="id"
                value={requestBody.id}
                onChange={handleChange}
              />
            </Grid>
            <Grid item paddingTop={2} padding={1}>
              <TextField
                id="outlined-basic"
                label="Gst-No."
                variant="outlined"
                fullWidth
                name="gstNumber"
                value={requestBody.gstNumber}
                onChange={handleChange}
              />
            </Grid>
            <Grid item paddingTop={2} padding={1}>
              <TextField
                id="outlined-basic"
                label="Address"
                variant="outlined"
                fullWidth
                name="address"
                value={employer.address}
                onChange={handleChange1}
              />
            </Grid>
            <Grid item paddingTop={2} padding={1}>
              <TextField
                id="outlined-basic"
                label="Location"
                variant="outlined"
                fullWidth
                name="location"
                value={employer.location}
                onChange={handleChange1}
              />
            </Grid>
            <Grid item paddingTop={2} m={1}>
              <Grid sm={10}></Grid>
              <Grid sm={2}>
                <Button variant="contained" type="submit">
                  Submit
                </Button>
              </Grid>
            </Grid>
          </form>
        </Container>
      </Box>
    </Stack>
  );
}
