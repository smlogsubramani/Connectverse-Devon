import { useState, useEffect } from 'react';
import axios from "axios";
import { useParams } from "react-router-dom";
import { Container, Row, Col } from 'react-bootstrap';
import { Button, Descriptions, PageHeader, Statistic, Tabs, Modal } from 'antd';
import { Award } from "react-bootstrap-icons";

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


const CourseDetail = () => {

    const { cid } = useParams();

    const [comments, setComments] = useState([]);

    const [activeTab, setActiveTab] = useState();
    const [detailsHidden, setDetailsHidden] = useState(false);
    const [roadMapHidden, setRoadMapHidden] = useState(true);
    const [videoHidden, setVideoHidden] = useState(true);
    const [featuresHidden, setFeaturesHidden] = useState(true);
    const [forumHidden, setForumHidden] = useState(true);

    const [course, setCourse] = useState(INITIALSTATE);
    const [loading, setLoading] = useState(false);

    const getACourses = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`${Data.AppSettings.baseUrl}/skill/${cid}`);
            setCourse(response.data);
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
        getACourses();
    }, []);


    const handleTabChange = (activeKey) => {
        setActiveTab(activeKey);
        if(activeKey == 1) {
            setDetailsHidden(false);
            setRoadMapHidden(true);
            setVideoHidden(true);
            setFeaturesHidden(true);
            setForumHidden(true);
        } else if (activeKey == 2) {
            setDetailsHidden(true);
            setRoadMapHidden(false);
            setVideoHidden(true);
            setFeaturesHidden(true);
            setForumHidden(true);
        } else if(activeKey == 3) {
            setDetailsHidden(true);
            setRoadMapHidden(true);
            setVideoHidden(false);
            setFeaturesHidden(true);
            setForumHidden(true);
        } else if(activeKey == 4) {
            setDetailsHidden(true);
            setRoadMapHidden(true);
            setVideoHidden(true);
            setFeaturesHidden(false);
            setForumHidden(true);
        } else {
            setDetailsHidden(true);
            setRoadMapHidden(true);
            setVideoHidden(true);
            setFeaturesHidden(true);
            setForumHidden(false);
        }
    }

    /* Thalavithi */
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();

    today = dd + '-' + mm + '-' + yyyy;

    const { TabPane } = Tabs;

    const renderContent = (column = 2) => (
        <Descriptions size="small" column={column}>
            <Descriptions.Item label="Provider">{ course.provider }</Descriptions.Item>
            <Descriptions.Item label="Skills Involved">
            <a>{ course.skillsGained }</a>
            </Descriptions.Item>
            <Descriptions.Item label="Job Tittles">
                { course.jobTittles }
            </Descriptions.Item>
            <Descriptions.Item label="Pre-Requirements">{ course.preRequirements }</Descriptions.Item>
            <Descriptions.Item label="Average Time To Finish Course">{ course.averageTimeToFinishCourse }</Descriptions.Item>
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

    return(
        <div>
            <div className="course-header">
                <PageHeader
                    className="site-page-header-responsive"
                    onBack={() => window.history.back()}
                    title={ course.tittle }
                    subTitle={ course.subTittle }
                    extra={[
                    // <Button key="3">Operation</Button>,
                    // <Button key="2">Operation</Button>,
                    <Button key="1" style={{ backgroundColor: "green", color: "white" }} >
                        <Award /> Top Grossing
                    </Button>,
                    ]}
                    footer={
                    <Tabs defaultActiveKey="1" activeKey={activeTab} onChange={handleTabChange}>
                        <TabPane tab="Details" key="1" />
                        <TabPane tab="Road Map" key="2" />
                        <TabPane tab="Video" key="3" />
                        <TabPane tab="Features" key="4" />
                        <TabPane tab="Forums" key="5" />
                    </Tabs>
                    }
                >
                    <Content extra={extraContent}>{renderContent()}</Content>
                </PageHeader>
            </div>
            <Container>
                <Row hidden={detailsHidden}>
                    <h4>Course Contents</h4>
                    <div className="course-contents">
                        <CourseContents courseContents={course.courseContents} />
                    </div>
                </Row>
                <div hidden={roadMapHidden}>
                    <h4 className='side-headings'>Road Map</h4>
                </div>
                <div hidden={videoHidden} className="course-video">
                    <h4 className='side-headings'>References Videos</h4>
                    <Row>
                        <Col sm={1}></Col>
                        <Col sm={10}>
                        <iframe  width="100%" height="700px" title="YouTube video player" frameborder="0" allow="picture-in-pictur; fullscreene" src={ course.youtubeUrl } allowFullScreen></iframe>
                        </Col>
                        <Col sm={1}></Col>
                    </Row>
                </div>
                <div hidden={featuresHidden}>
                    <h4>Features</h4>
                    <CourseFeatures />
                </div>
                <div hidden={forumHidden}>
                    <h4 className='side-headings'>Forums</h4>
                    <Button className='btn btn-secondary' onClick={handleAddComment}>Add Comment</Button>
                    {
                        comments.map(comment => (
                            <Comments key={comment.id} id={comment.id} comment={comment.comment} likesc={comment.likes} reportsc={comment.reports} commentedBy={comment.commentedBy} replies={comment.replies} />
                        ))
                    }
                </div>
            </Container>
        </div>
    );
};

export default CourseDetail;
