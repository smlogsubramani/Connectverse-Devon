import React from "react";
import { Row, Col } from "react-bootstrap";
import { AiFillStar } from "react-icons/ai";
const Projectcard = () => {
  return (
    <div className="wrapper">
      <Row>
        <Col className="project_img" sm>
          <img
            src="https://icon-library.com/images/java-icon-images/java-icon-images-4.jpg"
            alt="project_img"
          ></img>
        </Col>
        <Col className="projects_desc">
          <h3>Hotel-Management</h3>
          <span>
            <AiFillStar />
            <span>Required:Frontend/Backend Developer</span>
          </span>
          <span>
            <AiFillStar />
            <span>Required:Frontend/Backend Developer</span>
          </span>
          <span>
            <AiFillStar />
            <span>Required:Frontend/Backend Developer</span>
          </span>
        </Col>
      </Row>
    </div>
  );
};

export default Projectcard;
