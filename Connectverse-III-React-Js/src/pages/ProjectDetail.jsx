import { useState, useEffect } from 'react';
import axios from "axios";
import { useParams, NavLink, useNavigate } from "react-router-dom";
import { Container, Row, Col } from 'react-bootstrap';
import { Button, Descriptions, PageHeader, Spin, Tabs, Modal, message } from 'antd';
import {
    MDBBtn,
    MDBModal,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
} from 'mdb-react-ui-kit';
import { Award } from "react-bootstrap-icons";
import { getToken, getUserId } from '../utils/auth';

import CourseCard from '../components/course/Card';
import Notice from '../components/project/Notice';

import Data from "../data";

import "../styles/pages/project-detail.css";
import { fas } from '@fortawesome/free-solid-svg-icons';


const INITIALSTATE = {
    id: 0,
    tittle: `Connectverse`,
    subTittle: "Fucks You...",
    totalMembers: 10,
    description: 'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    detailedDescription: "A Site for Project Collaboratin. Created by Navin Durai and Naveen Kumar as New Year Resolution 2022, then later on plannd to make updates and registered this site for mart india hackathon. This project is not yet fully functional and not yet completed but we team of team of 6 members were working on it beyond our limits. so hope for this project to be completed before 2023. The main objective of this project is to make students involve in doing projects and their active participation in project collaboration.",
    durationInMonths: 8,
    projectType: "INTERMEDIATE",
    skills: Data.Courses,
    startedAt: "01-01-2022",
    isCompleted: false,
    projectAdminId: 111,
    projectAdminName: "Rajni",
    team: [
        {
            "id": 1,
            "firstName": "Nfn"
        }
    ],
    notices: [
        {
            id: 1,
            title: "Meet @ 9:45",
            postedBy: "Rajini"
        },
        {
            id: 2,
            title: "Yen Vazhi Thani Vazhi...",
            postedBy: "Rajini"
        },
    ]
};

const ProjectDetailPage = () => {

    const { pid } = useParams();
    const navigate = useNavigate();

    const [activeTab, setActiveTab] = useState();
    const [detailsHidden, setDetailsHidden] = useState(false);
    const [skillsHidden, setSkillsHidden] = useState(true);
    const [noticesHidden, setNoticesHidden] = useState(true);
    const [chatHidden, setChatHidden] = useState(true);
    const [addNoticeHidden, setAddNoticeHidden] = useState(false);
    const [title, setTittle] = useState("");
    const [confirmLoading, setConfirmLoading] = useState(false);


    const [scrollableModal, setScrollableModal] = useState(false);



    const [isTeamMate, setIsTeamMate] = useState(false);
    const [isProjectAdmin, setIsProjectAdmin] = useState(false);

    const [project, setProject] = useState(INITIALSTATE);
    const [loading, setLoading] = useState(false);

    const handleJoinProject = () => {
        setScrollableModal(!scrollableModal);
    }

    const handleOk = async () => {
        console.log(title);
        setConfirmLoading(true);
        const body = {
            message: title,
            sentBy: getUserId(),
            projectId: pid
        };
        const headers = { Authorization: getToken() };
        const response = await axios.post(`${baseUrl}/project/notice/add`, body, { headers });
        setTimeout(() => {
            if (response.status == 200) {
                message.success(response.data);
                getAProject();
                showNotice();
            }
            setAddNoticeHidden(false);
            setConfirmLoading(false);
        }, 2000);
    };

    const handleCancel = () => {
        console.log('Clicked cancel button');
        setAddNoticeHidden(false);
    };

    const handleChange = (e) => {
        setTittle(e.target.value);
    };

    const handleAgreementOk = async () => {
        const headers = { Authorization: getToken() };

        try {
            const request = await axios.get(`${baseUrl}/project/${project.id}/join/${getUserId()}`, { headers });
            if (request.status == 200) {
                message.success(request.data);
                setScrollableModal(!scrollableModal);
            }
        } catch (e) {
            console.log(e);
            message.error("Error Joining Proejct...")
        }

        // message.success("You are in project now...");
    }

    const handleAgreementCancel = () => {
        setScrollableModal(!scrollableModal);
        message.error("You can't join Job without accepting agrement....");
    }

    const handleProjectClose = () => {
        project.isCompleted = !project.isCompleted;
        setProject(project);
        console.log("Proj3ect: ", project);
        window.location.reload();
    }

    const baseUrl = Data.AppSettings.baseUrl;

    const getAProject = async () => {
        try {
            setLoading(true);
            message.warn("Initializing...");
            const headers = { Authorization: getToken() };
            const response = await axios.get(`${baseUrl}/project/${pid}`, { headers });
            setProject(response.data);
            if (response.status == 200) {
                console.log(response.data);
                // message.success("Successfully initialized...");

                const userId = getUserId();

                console.log(response.data)

                if (response.data.projectAdminId == userId) {
                    setIsProjectAdmin(true);
                    setIsTeamMate(true);
                    console.log("isTeamMate " + isTeamMate + " isProjectAdmin " + isProjectAdmin);
                }
                response.data.team.map(member => {
                    console.log(member)
                    if (member.id == userId) {
                        setIsTeamMate(true);
                        console.log("isTeamMate " + isTeamMate + " isProjectAdmin " + isProjectAdmin);
                    }
                });
            }
        } catch (e) {
            console.error(e);
            message.error("Error Fetchind Data...");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        // setProject(toSet);
        (!getUserId()) && navigate("/login");
        getAProject();
    }, []);


    const showDetail = () => {
        setDetailsHidden(false);
        setSkillsHidden(true);
        setNoticesHidden(true);
        setChatHidden(true);
    }

    const showSkills = () => {
        setDetailsHidden(true);
        setSkillsHidden(false);
        setNoticesHidden(true);
        setChatHidden(true);
    }

    const showNotice = () => {
        setDetailsHidden(true);
        setSkillsHidden(true);
        setNoticesHidden(false);
        setChatHidden(true);
    }

    const showChats = () => {
        setDetailsHidden(true);
        setSkillsHidden(true);
        setNoticesHidden(true);
        setChatHidden(false);
    }


    const handleTabChange = (activeKey) => {
        setActiveTab(activeKey);
        if (activeKey == 1) {
            showDetail();
        } else if (activeKey == 2) {
            showSkills();
        } else if (activeKey == 3) {
            showNotice();
        } else {
            showChats();
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
            <Descriptions.Item label="Project Id">{project.id}</Descriptions.Item>
            <Descriptions.Item label="Project Admin"><NavLink to={`/profile/${project.projectAdminId}`}>{project.projectAdminName}</NavLink></Descriptions.Item>
            <Descriptions.Item label="Team Count">{project.totalMembers}</Descriptions.Item>
            <Descriptions.Item label="Project Duration">
                <a>{project.durationInMonths} Months</a>
            </Descriptions.Item>
            <Descriptions.Item label="Project Type">
                {project.difficultyLevel}
            </Descriptions.Item>
            <Descriptions.Item label="Started On">{project.startedOn}</Descriptions.Item>
            <Descriptions.Item label="Description">{project.description}</Descriptions.Item>
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
                    title={project.tittle}
                    subTitle={project.subTittle}
                    extra={[
                        <Button hidden={!isProjectAdmin & !project.isCompleted} onClick={handleProjectClose} className="btn btn-outline-danger" key="3">Close Project</Button>,
                        <Button hidden={!isProjectAdmin & !project.isCompleted} onClick={() => { navigate(`/project/${project.id}/join`) }} className="btn btn-outline-info" key="3">View Joining requests</Button>,
                        <Button hidden={!isTeamMate | project.isCompleted} key="2" onClick={() => { setAddNoticeHidden(!addNoticeHidden) }} className='btn btn-warning'>Add Notice</Button>,
                        <Button hidden={isTeamMate | project.isCompleted} key="1" onClick={handleJoinProject} style={{ backgroundColor: "green", color: "white" }} >
                            <Award /> Join Project
                        </Button>,
                    ]}
                    footer={
                        <Tabs defaultActiveKey="1" activeKey={activeTab} onChange={handleTabChange}>
                            <TabPane tab="Details" key="1" />
                            <TabPane tab="Required Skills" key="2" />

                            {
                                (isTeamMate) ? (<TabPane tab="Notices" key="3" />) : ""
                            }

                            {
                                (isTeamMate) ? (<TabPane tab="Chat" key="4" />) : ""
                            }

                        </Tabs>
                    }
                >
                    <Content extra={extraContent}>{renderContent()}</Content>
                </PageHeader>
            </div>
            <Container>
                <Row hidden={detailsHidden}>
                    <h4>Project Details</h4>
                    <p className="project-content">{project.detailedDescription}</p>
                </Row>
                <div hidden={skillsHidden} className="course-video">
                    <h4 className='side-headings'>Skills</h4>
                    <Container>
                        <Row>
                            {
                                (project.skills.length != 0 & !loading) ?
                                    project.skills.map(skill => (
                                        <Col className="course-card">
                                            <CourseCard id={skill.id} imageUrl={skill.imageUrl} name={skill.tittle} jobsCanBeApplied={skill.jobsCanBeApplied} />
                                        </Col>
                                    )) : <div className="text-center">
                                        <Spin size="large" />
                                        <h1>Fetching Required Skills...</h1>
                                    </div>
                            }
                        </Row>
                    </Container>
                </div>
                <div hidden={noticesHidden}>
                    <h4>Notice</h4>
                    {
                        project.notices.map(notice => (
                            <Notice key={notice.id} id={notice.id} title={notice.message} postedBy={notice.sentBy} />
                        ))
                    }


                    <div className='agreement'>
                        {/* <MDBBtn onClick={() => setScrollableModal(!scrollableModal)}>LAUNCH DEMO MODAL</MDBBtn> */}

                        <Modal
                            onOk={handleAgreementOk}
                            onCancel={handleAgreementCancel}
                            visible={scrollableModal}>
                            <MDBModalDialog scrollable>
                                <MDBModalContent>
                                    <MDBModalHeader>
                                        <MDBModalTitle>Connectverse Project Agreement</MDBModalTitle>
                                        <MDBBtn
                                            className='btn-close'
                                            color='none'
                                            onClick={() => setScrollableModal(!scrollableModal)}
                                        ></MDBBtn>
                                    </MDBModalHeader>
                                    <MDBModalBody>
                                        <p>
                                            Every employee who has reached the age of 18 but has not yet reached the age of 60 is eligible to work.
                                        </p>


                                        <p>
                                            It is provided that where the employment of any person in an establishment is terminated due to the closure of the establishment for any reason other than a weekly or other recognised holiday, the wages earned by him shall be paid before the expiry of the second day from the day on which his employment is so terminated.
                                        </p>


                                        <p>
                                            Article 16(2) of the Indian Constitution states that an employee must not face discrimination in any situation while working.
                                        </p>


                                        <p>
                                            According to the right to employment in India, an employer in India has all the rights and entitlements to hire the best employee for their company.
                                        </p>


                                        <p>
                                            The employee has the right to be heard in relation to the termination of his employment. He must be given the opportunity to explain his position and demonstrate why he should not be dismissed or discharged.
                                        </p>
                                    </MDBModalBody>
                                </MDBModalContent>
                            </MDBModalDialog>
                        </Modal>
                    </div>

                    <Modal
                        title="Type Notice Here..."
                        visible={addNoticeHidden}
                        onOk={handleOk}
                        confirmLoading={confirmLoading}
                        onCancel={handleCancel}
                    >
                        <input className='form-control' name='title' value={title} onChange={handleChange} required />
                    </Modal>
                </div>
                <div hidden={chatHidden}>
                    <h4 className='side-headings'>Chat Here Privately...</h4>
                    {/* {
                        comments.map(comment => (
                            <Comments key={comment.id} id={comment.id} comment={comment.comment} likesc={comment.likes} reportsc={comment.reports} commentedBy={comment.commentedBy} replies={comment.replies} />
                        ))
                    } */}
                </div>
            </Container>
        </div>
    );
};

export default ProjectDetailPage;
