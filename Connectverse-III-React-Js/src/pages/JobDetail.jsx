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

import CourseCard from '../components/course/Card';
import Notice from '../components/project/Notice';
import { getToken, getUserId } from "../utils/auth";

import Data from "../data";

import "../styles/pages/project-detail.css";
import { fas } from '@fortawesome/free-solid-svg-icons';
import JobList from './JobList';


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
    workHoursPerWeek: "",
    isCompleted: false,
    projectAdmin: {
        id: 111,
        firstName: "Rajni"
    },
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

const JobDetailPage = () => {

    const { jid } = useParams();
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

    const [job, setJob] = useState(INITIALSTATE);
    const [loading, setLoading] = useState(false);

    const handleApplyJob = () => {
        console.log("applied");
        setScrollableModal(!scrollableModal);
    }

    const handleOk = () => {
        showNotice();
        console.log(title);
        setConfirmLoading(true);
        setTimeout(() => {
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
    }

    const handleAgreementOk = async () => {
        const userId = getUserId();
        const applied = job.employeesApplied.filter(employee => employee.id == getUserId());
        if (applied[0] == null) {
            setScrollableModal(!scrollableModal);
            const baseUrl = Data.AppSettings.baseUrl;
            const headers = { Authorization: getToken() };
            const response = await axios.get(`${baseUrl}/job/${jid}/apply/${userId}`, { headers });
            message.success(response.data);
        } else {
            message.warn("Already Applied For this Job...");
        }
    }

    const handleAgreementCancel = () => {
        setScrollableModal(!scrollableModal);
        message.error("You can't join project without accepting agrement....");
    }

    const handleProjectClose = () => {
        job.isCompleted = !job.isCompleted;
        setJob(job);
        console.log("job: ", job);
        window.location.reload();
    }

    const getOneJob = async () => {
        try {
            const userId = getUserId();
            console.log(userId);
            message.warn("Initializing...");
            setLoading(true);
            const baseUrl = Data.AppSettings.baseUrl;
            const headers = { Authorization: getToken() };
            const response = await axios.get(`${baseUrl}/job/${jid}`, { headers });
            setJob(response.data);
            console.log(response.data);
            if (response.data.employerId == userId) {
                console.log(response.data.employerId)
                console.log(userId);
                setIsProjectAdmin(true);
            } else {
                console.log(userId);
                console.log(response.data.employerId);
                console.log("doest not match...")
            }
        } catch (e) {
            console.error(e);
            message.error("Error Fetching Data...");
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        // setProject(toSet);
        (!getUserId()) && navigate("/login");
        getOneJob();
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

    const renderContent = (column = 3) => (
        <Descriptions size="small" column={column}>
            <Descriptions.Item label="Job Id">{job.id}</Descriptions.Item>
            <Descriptions.Item label="Title">{job.tittle}</Descriptions.Item>
            <Descriptions.Item label="Workers Wanted">{job.requiredWorkers}</Descriptions.Item>
            <Descriptions.Item label="Work Started">
                <a>{(job.workStarted) ? "Yes" : "No"}</a>
            </Descriptions.Item>
            <Descriptions.Item label="Work Type">
                {job.jobType}
            </Descriptions.Item>
            <Descriptions.Item label="Working Hours Per Week">{job.workHoursPerWeek} Hours</Descriptions.Item>
            <Descriptions.Item label="Pay Per Hour">{job.payPerHour} £</Descriptions.Item>
            {(!job.isTechnicalJob) && <Descriptions.Item label="No Of Working Days">{job.noOfDays}</Descriptions.Item>}
            <Descriptions.Item label="Location">{`${job.location} - ${job.state}`}</Descriptions.Item>
            <Descriptions.Item label="Technical Job">{(job.isTechnicalJob) ? "Yes" : "No"}</Descriptions.Item>
            {(!job.isTechnicalJob) && <Descriptions.Item label="Vehicle Wanted">{(job.vehicleWanted) ? "Yes" : "No"}</Descriptions.Item>}
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
                    title={job.tittle}
                    subTitle={job.subTittle}
                    extra={[
                        <Button hidden={!isProjectAdmin} onClick={handleProjectClose} className="btn btn-outline-danger" key="3">Hide Job</Button>,
                        <Button hidden={!isProjectAdmin} onClick={() => { navigate(`/job/${job.id}/join`) }} className="btn btn-outline-info" key="3">Joining requests</Button>,
                        <Button hidden={isProjectAdmin} key="1" onClick={handleApplyJob} style={{ backgroundColor: "green", color: "white" }} >
                            <Award /> {(job.employeesApplied != null && job.employeesApplied.filter(employee => employee.id == getUserId())[0] != null) ? "Applied" : "Easy Apply"}
                        </Button>,
                    ]}
                    footer={
                        <Tabs defaultActiveKey="1" activeKey={activeTab} onChange={handleTabChange}>
                            <TabPane tab="Details" key="1" />

                            {
                                (job.isTechnicalJob) ? (<TabPane tab="Required Skills" key="2" />) : ""
                            }
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
                    <p className="project-content">{job.description}</p>
                </Row>
                <div hidden={skillsHidden} className="course-video">
                    <h4 className='side-headings'>Skills</h4>
                    <Container>
                        <Row>
                            {
                                (job.skills != null && job.isTechnicalJob) ?
                                    job.skills.map(skill => (
                                        <Col className="course-card">
                                            <CourseCard id={skill.id} imageUrl={skill.imageUrl} name={skill.tittle} jobsCanBeApplied={skill.jobsCanBeApplied} />
                                        </Col>
                                    )) :
                                    <div className="text-center">
                                        <Spin size="large" />
                                        <h1>Fetching Required Skills...</h1>
                                    </div>
                            }
                        </Row>
                    </Container>
                </div>
                <div className='agreement'>
                    {/* <MDBBtn onClick={() => setScrollableModal(!scrollableModal)}>LAUNCH DEMO MODAL</MDBBtn> */}

                    <Modal
                        onOk={handleAgreementOk}
                        onCancel={handleAgreementCancel}
                        visible={scrollableModal}>
                        <MDBModalDialog scrollable>
                            <MDBModalContent>
                                <MDBModalHeader>
                                    <MDBModalTitle>Connectverse Job Agreement</MDBModalTitle>
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
            </Container>
        </div>
    );
};


export default JobDetailPage;
