import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import { message } from "antd";
import {
  Grid,
  Container,
  Stack,
  TextField,
  FormControlLabel,
  Radio,
  RadioGroup,
  FormGroup,
  FormLabel,
  Select,
  InputLabel,
  MenuItem,
  FormControl
} from "@mui/material";

import { getUserId } from "../utils/auth";

import Data from "../data";
import Signupimg from "../assets/images/Signup.jpg";
import "../styles/pages/Signup2.css";


const INITIAL_USER = {
  "firstName": "",
  "lastName": "",
  "age": 18,
  "gender": "",
  "cvUrl": "",
  "aadharId": "",
  "email": "",
  "mobileNumber": "",
  "address": "",
  "location": "",
  "state": "",
  "readyToRelocate": false
};

const Signup2 = () => {

  const navigate = useNavigate();
  const [states, setStates] = useState([]);
  const [requestBody, setRequestBody] = useState(INITIAL_USER);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRequestBody(prev => ({ ...prev, [name]: value }));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Submitted...");

    const baseUrl = Data.AppSettings.baseUrl;

    try {
      setLoading(true);
      setError("");

      const url = `${baseUrl}/auth/register/employee`;

      console.log(requestBody);

      const response = await axios.post(url, requestBody);
      if (response.status == 201) {
        navigate("/login");
      }

    } catch (e) {
      setError(e);
      console.log(e);
      message.error("Unable To Login at this Moment...");
    } finally {
      setLoading(false);
    }
  }

  const initState = async () => {
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
    (getUserId()) && navigate("/");
    initState();
  }, [requestBody]);

  return (
    <>
      <Container maxWidth="lg" sx={{ marginTop: "100px" }}>
        <Grid container>
          <Grid md={7} sm={0} xs={0}>
            <img src={Signupimg} alt="signup-img" className="signup-img" />
          </Grid>
          <Grid md={5} sm={12} xs={12}>
            <div className="form-center">
              <h1 className="logo">ConnectVerse</h1>
              <p className="desc">Welcome to Connectverse </p>
              <form className="form-scrollable" onSubmit={handleSubmit}>
                <Stack spacing={4}>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="FirstName"
                      variant="standard"
                      name="firstName"
                      value={requestBody.firstName}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="LastName"
                      variant="standard"
                      name="lastName"
                      value={requestBody.lastName}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Age"
                      variant="standard"
                      name="age"
                      value={requestBody.age}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <FormGroup>
                      <FormLabel>Gender</FormLabel>
                      <RadioGroup
                        aria-labelledby="demo-controlled-radio-buttons-group"
                        name="gender"
                        value={requestBody.gender}
                        onChange={handleChange}
                      >
                        <FormControlLabel
                          value="FEMALE"
                          control={<Radio />}
                          label="Female"
                        />
                        <FormControlLabel
                          value="MALE"
                          control={<Radio />}
                          label="Male"
                        />
                      </RadioGroup>
                    </FormGroup>
                  </div>

                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="CV-url"
                      variant="standard"
                      name="cvUrl"
                      value={requestBody.cvUrl}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Aadhaar-id"
                      variant="standard"
                      name="aadharId"
                      value={requestBody.aadharId}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Email"
                      variant="standard"
                      name="email"
                      value={requestBody.email}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="MobileNumber"
                      variant="standard"
                      name="mobileNumber"
                      value={requestBody.mobileNumber}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Address"
                      variant="standard"
                      name="address"
                      value={requestBody.address}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Location"
                      variant="standard"
                      name="location"
                      value={requestBody.location}
                      onChange={handleChange}
                    />
                  </div>
                  <div className="input-firstname">
                    {/* <TextField
                      id="standard-basic"
                      label="State"
                      variant="standard"
                      name="state"
                      value={requestBody.state}
                      onChange={handleChange}
                    /> */}
                    <FormControl fullWidth>
                      <InputLabel id="demo-simple-select-label">State</InputLabel>
                      <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        name="state"
                        value={requestBody.state}
                        label="State"
                        onChange={handleChange}
                      >
                        {
                          states.map(state => (
                            <MenuItem value={state}>{state}</MenuItem>
                          ))
                        }
                      </Select>
                    </FormControl>
                  </div>
                  <div className="input-firstname">
                    <FormGroup>
                      <FormLabel>Ready to Relocate </FormLabel>
                      <RadioGroup
                        aria-labelledby="demo-controlled-radio-buttons-group"
                        name="readyToRelocate"
                        value={requestBody.readyToRelocate}
                        onChange={handleChange}
                      >
                        <FormControlLabel
                          value={true}
                          control={<Radio />}
                          label="Yes"
                        />
                        <FormControlLabel
                          value={false}
                          control={<Radio />}
                          label="No"
                        />
                      </RadioGroup>
                    </FormGroup>
                  </div>
                  {/* <div className="input-firstname">
                    <FormGroup>
                      <FormLabel>Do yo have Driving Licence </FormLabel>
                      <RadioGroup
                        aria-labelledby="demo-controlled-radio-buttons-group"
                        name="controlled-radio-buttons-group"
                        value={value2}
                        onChange={handleChange2}
                      >
                        <FormControlLabel
                          value="Yes"
                          control={<Radio />}
                          label="Yes"
                        />
                        <FormControlLabel
                          value="No"
                          control={<Radio />}
                          label="No"
                        />
                      </RadioGroup>
                    </FormGroup>
                  </div>
                  <div className="input-firstname">
                    <FormGroup>
                      <FormLabel>Do you have Vechile </FormLabel>
                      <RadioGroup
                        aria-labelledby="demo-controlled-radio-buttons-group"
                        name="controlled-radio-buttons-group"
                        value={value3}
                        onChange={handleChange3}
                      >
                        <FormControlLabel
                          value="Yes"
                          control={<Radio />}
                          label="Yes"
                        />
                        <FormControlLabel
                          value="No"
                          control={<Radio />}
                          label="No"
                        />
                      </RadioGroup>
                    </FormGroup>
                  </div>
                  <div className="input-firstname">
                    <TextField
                      id="standard-basic"
                      label="Physical Health Points"
                      variant="standard"
                    />
                  </div> */}
                </Stack>
                <button className="signup-btn">Register</button>
                <div className="signin">
                  <a href="/" className="signin-navigator">
                    Already have an account?
                  </a>
                </div>
              </form>
            </div>
          </Grid>
        </Grid>
      </Container>
    </>
  );
};

export default Signup2;
