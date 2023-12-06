import { useState, useEffect } from "react";
import axios from "axios";
import { Container, Row, Col } from "react-bootstrap";
import { Spin } from 'antd';

import "../styles/pages/course-list.css";

import Data from "../data/index.json";
import CompanyCard from "../components/company/CompanyCard";


const CompanyList = () => {

    const [companies, setCompany] = useState([]);
    const [loading, setLoading] = useState(false);

    const getCourses = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`${Data.AppSettings.baseUrl}/company`);
            setCompany(response.data);
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
                        (companies.length != 0 & !loading) ?
                        companies.map(company => (
                            <Col className="course-card">
                                <CompanyCard id={company.id} imageUrl={company.imageUrl} name={company.name} ownedBy={company.ownedBy} />
                            </Col>
                        )) : <div className="text-center">
                                <Spin size="large" />
                                <h1>Fetching Companies...</h1>
                            </div>
                    }
                </Row>
            </Container>
        </div>
    );
};

export default CompanyList;
