import { useNavigate } from 'react-router-dom';

import { ArrowReturnRight } from 'react-bootstrap-icons';
import { InfoCircleOutlined, SettingOutlined } from '@ant-design/icons';
import { Avatar, Card } from 'antd';
const { Meta } = Card;

const CompanyCard = ({ id, imageUrl, name, ownedBy }) => {
    const navigate = useNavigate();

    return(
        <div onClick={() => { navigate(`/company/${id}`) }}>
            <Card
                style={{
                width: 300,
                backgroundColor: "#E5E5E5",
                borderRadius: "2%",
                boxShadow: "5px 5px 5px #aaaaaa",
                border: "1px solid #bfbfbf",
                cursor: "pointer",
            }}
                cover={
                <img
                    className="course-list-img"
                    alt="example"
                    src={imageUrl}
                />
                }
                actions={[
                // <SettingOutlined key="setting" />,
                <InfoCircleOutlined key="edit" />,
                <ArrowReturnRight key="ellipsis" />,
                ]}
            >
                <Meta
                avatar={<Avatar src="https://avatars.githubusercontent.com/u/71096790?v=4" />}
                title={name}
                description={ownedBy}
                />
            </Card>
        </div>
    );
};

export default CompanyCard;
