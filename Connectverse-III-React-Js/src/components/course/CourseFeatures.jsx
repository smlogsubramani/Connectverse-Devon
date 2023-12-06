import { Row, Col } from 'react-bootstrap';
import { AwardFill, AlarmFill, BookFill, TrophyFill } from "react-bootstrap-icons";

const CourseFeatures = () => (
    <div className="course-features">
        <Row>
            <Col sm={6}>
                <div className="course-feature">
                    <div className='feature-icon'><AwardFill size={50} /></div>
                    <h4>Up-to-Date</h4>
                    <p>
                        Technology is updating day-to-day our IT Team will keep the course updated regularly no worries.
                    </p>
                </div>
            </Col>
            <Col sm={6}>
                <div className="course-feature">
                    <div className='feature-icon'><AlarmFill size={50} /></div>
                    <h4>Flexible Timings</h4>
                    <p>
                        The courses are customized to the preferences of the students and the session timing is designed to be flexible.
                    </p>
                </div>
            </Col>
            <Col sm={6}>
                <div className="course-feature">
                    <div className='feature-icon'><BookFill size={50} /></div>
                    <h4>Communical Forum</h4>
                    <p>
                        We have created a {'Q&A'} community forum, where you can converse and develop your skills.
                    </p>
                </div>
            </Col>
            <Col sm={6}>
                <div className="course-feature">
                    <div className='feature-icon'><TrophyFill size={50} /></div>
                    <h4>Job Support</h4>
                    <p>
                        We offer you job placement guidance by providing rich interview questions materials and job recruitment
                    </p>
                </div>
            </Col>
        </Row>
    </div>
);

export default CourseFeatures;