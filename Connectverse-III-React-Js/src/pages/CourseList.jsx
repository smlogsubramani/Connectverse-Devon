import { useState, useEffect } from "react";
import axios from "axios";
import { Container, Row, Col } from "react-bootstrap";
import { Spin } from 'antd';

import CourseCard from "../components/course/Card";
import "../styles/pages/course-list.css";

import Data from "../data/index.json";


const CourseList = () => {

    const [courses, setCourses] = useState(Data.Courses);
    const [loading, setLoading] = useState(false);

    const getCourses = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`${Data.AppSettings.baseUrl}/skill`);
            setCourses(response.data);
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        getCourses();
    }, []);

    return (
        <div>
            <Container>
                <Row>
                    {
                        (courses.length != 0 & !loading) ?
                        courses.map(course => (
                            <Col className="course-card">
                                <CourseCard id={course.id} imageUrl={course.imageUrl} name={course.tittle} jobsCanBeApplied={course.jobsCanBeApplied} />
                            </Col>
                        )) : <div className="text-center">
                                <Spin size="large" />
                                <h1>Fetching Courses...</h1>
                            </div>
                    }
                </Row>
            </Container>
        </div>
    );
};

export default CourseList;
