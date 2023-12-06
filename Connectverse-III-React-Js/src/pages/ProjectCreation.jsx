import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
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
import Multiselect from 'multiselect-react-dropdown';

import { message } from 'antd';

import Data from "../data";
import "../styles/pages/project-creation.css";
import { getEmployeeId, getToken } from "../utils/auth";

const { Option } = Select;


const INITIAL_PROJECT = {
  "tittle": "",
  "subTittle": "",
  "description": "",
  "detailedDescription": "",
  "durationInMonths": 0,
  "difficultyLevel": "",
  "totalMembers": 0,
  "isCompleted": false,
  projectAdminId: "",
  "skillIds": [
  ]
};

const INITIAL_SKILLS = [
  {
    id: 0,
    tittle: "",
    imageUrl: ""
  }
]


const ProjectCreation = () => {
  const navigate = useNavigate();

  const [skills, setSkills] = useState(INITIAL_SKILLS);
  const [projectSkills, setProjectSkills] = useState([]);
  const [requestBody, setRequestBody] = useState(INITIAL_PROJECT);
  const [loading, setLoading] = useState(false);

  const handleSelect = (projectSkillsArg, selectedItem) => {
    console.log(projectSkillsArg);
    setProjectSkills(projectSkillsArg);
  }

  const handleRemove = (projectSkillsArg, removedItem) => {
    console.log(projectSkillsArg);
    setProjectSkills(projectSkillsArg);
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setRequestBody(prev => ({ ...prev, [name]: value }));
  }

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
      message.error("Uanble to Connect to Server!.")
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
      projectSkills.map(skill => {
        console.log("ID", skill.id)
        requestBody.skillIds.push(skill.id);
        setRequestBody(requestBody);
      });
      requestBody.projectAdminId = getEmployeeId();
      console.log(requestBody);
      const response = await axios.post(`${baseUrl}/project`, requestBody, { headers });
      if(response.status == 200) {
        navigate(`/profile/${getEmployeeId()}`);
      }
    } catch(e) {
      message.error("Unable to create Prpject at this moment...");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    console.log("Emp Id", getEmployeeId());
    (!getEmployeeId() &&  navigate("/login"));
    initSkills();
  }, [requestBody]);

  return (
    <Stack>
      <Box sx={{ padding: 4 }}>
        <Container>
          <Typography variant="h6" align="left" padding={1}>
            Project Creation Form
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
                    label="Sub-Title"
                    variant="outlined"
                    fullWidth
                    name="subTittle"
                    value={requestBody.subTittle}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
              </Grid>

              <Grid container item spacing={2}>
                <Grid container item spacing={2}>
                  <Grid item xs={12} md={6} spacing={2}>
                    <TextField
                      id="outlined-basic"
                      label="Duration(months)"
                      variant="outlined"
                      fullWidth
                      name="durationInMonths"
                      value={requestBody.durationInMonths}
                      onChange={handleChange}
                      required
                    ></TextField>
                  </Grid>
                  <Grid item xs={12} md={6} spacing={2}>
                    <TextField
                      id="outlined-basic"
                      label="Total-Members"
                      variant="outlined"
                      fullWidth
                      name="totalMembers"
                      value={requestBody.totalMembers}
                      onChange={handleChange}
                      required
                    ></TextField>
                  </Grid>
                </Grid>
              </Grid>
              <Grid container item spacing={2}>
                <Grid item xs={12} md={6} spacing={2}>
                  <TextField
                    id="outlined-basic"
                    label="Description"
                    variant="outlined"
                    fullWidth
                    name="description"
                    value={requestBody.description}
                    onChange={handleChange}
                    required
                  ></TextField>
                </Grid>
                <Grid item xs={12} md={6} spacing={2}>
                  <FormControl fullWidth>
                    <InputLabel id="demo-simple-select-label">
                      Difficulty-Level
                    </InputLabel>
                    <Select
                      labelId="demo-simple-select-label"
                      id="demo-simple-select"
                      value={requestBody.difficultyLevel}
                      label="Difficulty-Level"
                      name="difficultyLevel"
                      onChange={handleChange}
                    >
                      <MenuItem value="BEGINNERS">BEGINNERS</MenuItem>
                      <MenuItem value="INTERMEDIATE">INTERMEDIATE</MenuItem>
                      <MenuItem value="EXPERT">EXPERT</MenuItem>
                    </Select>
                  </FormControl>
                </Grid>
              </Grid>
              <Grid container item>
                <Multiselect
                  placeholder="Select the skills required to work in this project"
                  style={{
                    width: '100%',
                  }}
                  options={skills} // Options to display in the dropdown
                  selectedValues={projectSkills} // Preselected value to persist in dropdown
                  onSelect={handleSelect} // Function will trigger on select event
                  onRemove={handleRemove} // Function will trigger on remove event
                  displayValue="name" // Property name to display in the dropdown options
                />
              </Grid>
              <Grid container item>
                <TextField
                  id="outlined-basic"
                  label="Detailed-description"
                  variant="outlined"
                  fullWidth
                  name="detailedDescription"
                  value={requestBody.detailedDescription}
                  onChange={handleChange}
                  required
                />
              </Grid>
              <Grid container padding={2}>
                <Grid item spacing={3}>
                  <Button variant="contained" type="submit">Submit</Button>
                </Grid>
              </Grid>
            </Grid>
          </form>
        </Container>
      </Box>
    </Stack>
  );
};

export default ProjectCreation;
