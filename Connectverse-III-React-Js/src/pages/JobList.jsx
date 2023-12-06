import { useEffect, useState, createElement } from 'react';
import axios from "axios";
import { useNavigate, NavLink } from 'react-router-dom';
import { Container } from "react-bootstrap";
import { People } from 'react-bootstrap-icons';
import { LikeOutlined, EyeOutlined } from '@ant-design/icons';
import { Avatar, List, Space, message } from 'antd';

import Data from "../data";

import "../styles/pages/job-list.css";


const INITIAL_DATA = {
    id: 1,
    title: `Job Tittle 1`,
    subTittle: "Fuck You...",
    totalMembers: 10,
    description: 'This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me',
    detailedDescription: "",
    durationInMonths: 8,
    projectType: "INTERMEDIATE",
    skills: Data.Courses,
    content: 'This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me.',
}

const data = Array.from({
    length: 23,
}).map((_, i) => ({
    id: i,
    title: `Job Tittle ${i}`,
    subTittle: "Fuck You...",
    totalMembers: 10,
    description: 'This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me',
    detailedDescription: "",
    durationInMonths: 8,
    projectType: "INTERMEDIATE",
    skills: Data.Courses,
    content: 'This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me.',
}));

const IconText = ({ icon, text }) => (
    <Space>
        {createElement(icon)}
        {text}
    </Space>
);

const JobList = () => {

    const navigate = useNavigate();

    const [jobs, setJobs] = useState([]);

    const initializeJobs = async () => {
        try {
            message.warn("initialized...");
            const baseUrl = Data.AppSettings.baseUrl;
            const response = await axios.get(`${baseUrl}/job`);
            setJobs(response.data);
        } catch(e) {
            console.log(e);
            message.error("Error initializing...");
        }
    };

    useEffect(() => {
        initializeJobs();
    }, []);

    return (
        <Container>
            <List
                itemLayout="vertical"
                size="large"
                pagination={{
                    onChange: (page) => {
                        console.log(page);
                    },
                    pageSize: 6,
                }}
                dataSource={jobs}
                // footer={
                //   <div>
                //     <b>ant design</b> footer part
                //   </div>
                // }
                renderItem={(item) => (
                    <div className='project-list-card' onClick={() => { navigate(`/job/${item.id}`) }}>
                        <List.Item
                            key={item.id}
                            actions={[
                                <IconText icon={EyeOutlined} text="156" key="list-vertical-star-o" />,
                                <IconText icon={LikeOutlined} text="156" key="list-vertical-like-o" />,
                                <IconText icon={People} text={item.requiredWorkers} key="list-vertical-message" />,
                            ]}
                            extra={
                                <img
                                    width={272}
                                    alt="logo"
                                    src={item.company.imageUrl}
                                />
                            }
                        >
                            <List.Item.Meta
                                avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
                                title={<NavLink to={`/project/${item.id}`}>{item.tittle}</NavLink>}
                                description={`${item.location} - ${item.state}`}
                            />
                            {item.description}
                        </List.Item>
                    </div>
                )}
            />
        </Container>
    );
};

export default JobList;
