import { useState, useEffect, createElement } from "react";
import { useNavigate, NavLink } from "react-router-dom";
import axios from "axios";
import { useParams } from "react-router-dom";
import { Container, Row, Col } from 'react-bootstrap';
import { Button, Descriptions, PageHeader, Statistic, Tabs, Modal } from 'antd';
import { EyeOutlined, CalendarOutlined } from "@ant-design/icons";
import { Avatar, List, Space, message } from "antd";
import { People } from "react-bootstrap-icons";

import { CheckCircleFill } from "react-bootstrap-icons";
import { getToken, getUserId } from "../utils/auth";

// import CourseHeader from "../components/course/CourseHeader";
import CourseContents from "../components/course/CourseContents";
import CourseFeatures from "../components/course/CourseFeatures";
import Comments from "../components/forums/Comments";

import Data from "../data";

import "../styles/pages/course-detail.css";
import { fas } from '@fortawesome/free-solid-svg-icons';


const INITIALSTATE = {
    "id": 1,
    "tittle": "",
    "subTittle": "",
    "jobsCanBeApplied": "",
    "provider": "",
    "skills": "",
    "jobTittles": "",
    "preRequirements": "Basic Computer knowledge.",
    "averageTimeToFinishCourse": " Days",

    "roadMapUrl": "",
    "youtubeUrl": "",
    "useFulLink": "",
    "imageUrl": "",
    "courseContents": [
        {
            "id": "a",
            "tittle": "Will Be added Soon",
            "subTittles": [
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon"
            ]
        },
        {
            "id": "b",
            "tittle": "Will Be added Soon",
            "subTittles": [
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon"
            ]
        },
        {
            "id": "c",
            "tittle": "Will Be added Soon",
            "subTittles": [
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon",
                "Will Be added Soon"
            ]
        }
    ]
};

const INITIALCOMMENTS = [
    {
        id: 1,
        likes: 10,
        reports: 100,
        comment: "We supply a series of design principles, practical patterns and high quality design resources to help people create their product prototypes beautifully and efficiently.",
        commentedBy: "suni",
        replies: [
            {
                id: 1,
                likes: 10,
                reports: 100,
                commentedBy: "suni",
                reply: "Fuck You"
            },
            {
                id: 2,
                likes: 100,
                reports: 10,
                commentedBy: "suni",
                reply: "sam suni"
            }
        ],
    },
    {
        id: 2,
        likes: 10,
        reports: 100,
        comment: "We supply a series of design principles, practical patterns and high quality design resources to help people create their product prototypes beautifully and efficiently.",
        commentedBy: "suni",
        replies: [
            {
                id: 1,
                likes: 10,
                reports: 100,
                commentedBy: "suni",
                reply: "Fuck You"
            },
            {
                id: 2,
                likes: 100,
                reports: 10,
                commentedBy: "suni",
                reply: "sam suni"
            }
        ],
    },
    {
        id: 3,
        likes: 10,
        reports: 100,
        comment: "We supply a series of design principles, practical patterns and high quality design resources to help people create their product prototypes beautifully and efficiently.",
        commentedBy: "suni",
        replies: [
            {
                id: 1,
                likes: 10,
                reports: 100,
                commentedBy: "suni",
                reply: "Fuck You"
            },
            {
                id: 2,
                likes: 100,
                reports: 10,
                commentedBy: "suni",
                reply: "sam suni"
            }
        ]
    }
];

const IconText = ({ icon, text }) => (
    <Space>
        {createElement(icon)}
        {text}
    </Space>
);

const CompanyDetail = () => {
    const navigate = useNavigate();

    const { cid } = useParams();

    const [comments, setComments] = useState([]);

    const [activeTab, setActiveTab] = useState();
    const [detailsHidden, setDetailsHidden] = useState(false);
    const [roadMapHidden, setRoadMapHidden] = useState(true);
    const [videoHidden, setVideoHidden] = useState(true);
    const [featuresHidden, setFeaturesHidden] = useState(true);
    const [forumHidden, setForumHidden] = useState(true);

    const [company, setCompany] = useState(INITIALSTATE);
    const [loading, setLoading] = useState(false);

    const getACompany = async () => {
        try {
            setLoading(true);
            const headers = { Authorization: getToken() };
            const response = await axios.get(`${Data.AppSettings.baseUrl}/company/${cid}`, { headers });
            setCompany(response.data);
            setComments(response.data.comments);
        } catch (e) {
            console.error(e);
        } finally {
            setLoading(false);
        }
    }

    const handleAddComment = () => {

    }


    useEffect(() => {
        // const fetchedCourse = Data.Courses.filter(course => (course.id == cid))[0];

        // const toSet = (fetchedCourse.length === 0) ? INITIALSTATE : fetchedCourse;
        // setCourse(toSet);
        (!getUserId()) && navigate("/login");
        getACompany();
    }, []);


    const handleTabChange = (activeKey) => {
        setActiveTab(activeKey);
        if (activeKey == 1) {
            setDetailsHidden(false);
            setRoadMapHidden(true);
        } else {
            setDetailsHidden(true);
            setRoadMapHidden(false);
        }
    }

    /* Thalaviti */
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = dd + '-' + mm + '-' + yyyy;

    const { TabPane } = Tabs;

    const renderContent = (column = 2) => (
        <Descriptions size="small" column={column}>
            <Descriptions.Item label="Id">{company.id}</Descriptions.Item>
            <Descriptions.Item label="Name">
                <a>{company.name}</a>
            </Descriptions.Item>
            <Descriptions.Item label="Employees In Company">
                {company.noOfEmployees}
            </Descriptions.Item>
            <Descriptions.Item label="Owned-By">{company.ownedBy}</Descriptions.Item>
            {/* <Descriptions.Item label="Jobs Posted">{company.jobs}</Descriptions.Item> */}
        </Descriptions>
    );

    const extraContent = (
        <div
            style={{
                display: 'flex',
                width: 'max-content',
                justifyContent: 'flex-end',
            }}
        >
            {/* <Statistic
                title="Status"
                value={ status }
                style={{
                    marginRight: 32,
                }}
            /> */}
            {/* <Statistic title="Price" prefix="₹" value={ price } /> */}
        </div>
    );

    const Content = ({ children, extra }) => (
        <div className="content">
            <div className="main">{children}</div>
            <div className="extra">{extra}</div>
        </div>
    );

    return (
        <div>
            <div className="course-header">
                <PageHeader
                    className="site-page-header-responsive"
                    onBack={() => window.history.back()}
                    title={company.tittle}
                    subTitle={company.subTittle}
                    extra={[
                        // <Button key="3">Operation</Button>,
                        // <Button key="2">Operation</Button>,
                        <Button key="1" style={{ backgroundColor: "green", color: "white" }} >
                            <CheckCircleFill />&nbsp; Verified
                        </Button>,
                    ]}
                    footer={
                        <Tabs defaultActiveKey="1" activeKey={activeTab} onChange={handleTabChange}>
                            <TabPane tab="Details" key="1" />
                            <TabPane tab="Jobs" key="2" />
                        </Tabs>
                    }
                >
                    <Content extra={extraContent}>{renderContent()}</Content>
                </PageHeader>
            </div>
            <Container>
                <Row hidden={detailsHidden}>
                    <h4>Company Description</h4>
                    <div className="course-contents">
                        <p>{company.description}</p>
                    </div>
                </Row>
                <Row hidden={roadMapHidden}>
                    <h4>Jobs</h4>
                    <div className="course-contents">
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
                                dataSource={(company.jobs != []) ? company.jobs : []}
                                // footer={
                                //   <div>
                                //     <b>ant design</b> footer part
                                //   </div>
                                // }
                                renderItem={(item) => (
                                    <div
                                        className="project-list-card"
                                        onClick={() => {
                                            navigate(`/job/${item.id}`);
                                        }}
                                    >
                                        <List.Item
                                            key={item.title}
                                            actions={[
                                                <IconText
                                                    icon={EyeOutlined}
                                                    text="156"
                                                    key="list-vertical-star-o"
                                                />,
                                                <IconText
                                                    icon={CalendarOutlined}
                                                    text={item.noOfEmployees}
                                                    key="list-vertical-like-o"
                                                />,
                                                <IconText
                                                    icon={People}
                                                    text={item.noOfEmployees}
                                                    key="list-vertical-message"
                                                />,
                                            ]}
                                            extra={
                                                <img
                                                    width={272}
                                                    alt="logo"
                                                    src={`${company.imageUrl}`}
                                                />
                                            }
                                        >
                                            <List.Item.Meta
                                                avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
                                                title={
                                                    <div>
                                                        <NavLink to={`/job/${item.id}`}>{item.tittle}</NavLink>
                                                        <p style={{ fontSize: "10px" }} >{item.jobType}</p>
                                                    </div>
                                                }
                                                description={item.state}
                                            />
                                            {item.description}
                                        </List.Item>
                                    </div>
                                )}
                            />
                        </Container>
                    </div>
                </Row>
            </Container>
        </div>
    );
};

export default CompanyDetail;
