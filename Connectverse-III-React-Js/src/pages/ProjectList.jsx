import { useState, useEffect, createElement } from "react";
import axios from "axios";
import { useNavigate, NavLink } from "react-router-dom";
import { Container } from "react-bootstrap";
import { People } from "react-bootstrap-icons";
import { EyeOutlined, CalendarOutlined } from "@ant-design/icons";
import { Avatar, List, Space, message } from "antd";

import Data from "../data";

import "../styles/pages/project-list.css";

const data = Array.from({
  length: 23,
}).map((_, i) => ({
  id: i,
  title: `Project Tittle ${i}`,
  subTittle: "Fuck You...",
  totalMembers: 10,
  description:
    "This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me",
  detailedDescription: "",
  durationInMonths: 8,
  skillImage: "https://joeschmoe.io/api/v1/random",
  content: "This project is basically designed to take up my life and sleep and also litttle amount f existing peace from me, This project is basically designed to take up my life and sleep and also litttle amount f existing peace from meThis project is basically designed to take up my life and sleep and also litttle amount f existing peace from me"
}));

const IconText = ({ icon, text }) => (
  <Space>
    {createElement(icon)}
    {text}
  </Space>
);

const ProjectListPage = () => {
  const navigate = useNavigate();

  const [projects, setProjects] = useState([]);

  const baseUrl = Data.AppSettings.baseUrl;

  const initProjects = async () => {
    try {
      message.warn("Fetching Projects...");
      const response = await axios.get(`${baseUrl}/project`);
      console.log(response.data);
      setProjects(response.data);
      if (response.status == 200) {
        message.success("Successfully initialized...");
        console.log(projects[0]);
      }
    } catch (e) {
      message.error("Error Fetchind Data...");
      console.log(e);
    }
  }

  useEffect(() => {
    initProjects();
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
        dataSource={projects}
        // footer={
        //   <div>
        //     <b>ant design</b> footer part
        //   </div>
        // }
        renderItem={(item) => (
          <div
            className="project-list-card"
            onClick={() => {
              navigate(`/project/${item.id}`);
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
                  text={item.durationInMonths}
                  key="list-vertical-like-o"
                />,
                <IconText
                  icon={People}
                  text={item.totalMembers}
                  key="list-vertical-message"
                />,
              ]}
              extra={
                <img
                  width={272}
                  alt="logo"
                  src={`${item.skillImage}`}
                />
              }
            >
              <List.Item.Meta
                avatar={<Avatar src="https://joeschmoe.io/api/v1/random" />}
                title={
                  <div>
                    <NavLink to={`/project/${item.id}`}>{item.tittle}</NavLink>
                    <p style={{ fontSize: "10px" }} >{ item.subTittle }</p>
                  </div>
                }
                description={item.description}
              />
              {item.content}
            </List.Item>
          </div>
        )}
      />
    </Container>
  );
};

export default ProjectListPage;
