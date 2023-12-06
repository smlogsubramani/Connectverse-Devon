// import { Button, Descriptions, PageHeader, Statistic, Tabs } from 'antd';
// import { Award } from "react-bootstrap-icons";


// const CourseHeader = ({ tittle, subTittle, provider, instructor, effectiveCourseHours, instructionMode, noOfSessions }) => {

//     var today = new Date();
//     var dd = String(today.getDate()).padStart(2, '0');
//     var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
//     var yyyy = today.getFullYear();

//     today = dd + '-' + mm + '-' + yyyy;

//     const { TabPane } = Tabs;

//     const renderContent = (column = 2) => (
//         <Descriptions size="small" column={column}>
//             <Descriptions.Item label="Instructor">{ instructor }</Descriptions.Item>
//             <Descriptions.Item label="Course Duration">
//             <a>{ noOfSessions }</a>
//             </Descriptions.Item>
//             <Descriptions.Item label="Instruction Mode">
//                 { instructionMode }
//             </Descriptions.Item>
//             {/* <Descriptions.Item label="Sessions From">{ today }</Descriptions.Item> */}
//             <Descriptions.Item label="Effective Time">{ effectiveCourseHours } HRS</Descriptions.Item>
//         </Descriptions>
//     );

//     const extraContent = (
//         <div
//             style={{
//             display: 'flex',
//             width: 'max-content',
//             justifyContent: 'flex-end',
//             }}
//         >
//             {/* <Statistic
//                 title="Status"
//                 value={ status }
//                 style={{
//                     marginRight: 32,
//                 }}
//             /> */}
//             <Statistic title="Price" prefix="₹" value={ price } />
//         </div>
//     );

//     const Content = ({ children, extra }) => (
//         <div className="content">
//             <div className="main">{children}</div>
//             <div className="extra">{extra}</div>
//         </div>
//     );

//     return (
//         <PageHeader
//             className="site-page-header-responsive"
//             onBack={() => window.history.back()}
//             title={ tittle }
//             subTitle={ subTittle }
//             extra={[
//             // <Button key="3">Operation</Button>,
//             // <Button key="2">Operation</Button>,
//             <Button key="1" style={{ backgroundColor: "green", color: "white" }} >
//                 <Award /> Top Grossing
//             </Button>,
//             ]}
//             // footer={
//             // <Tabs defaultActiveKey="1">
//             //     <TabPane tab="Details" key="1" />
//             //     <TabPane tab="Contents" key="2" />
//             // </Tabs>
//             // }
//         >
//             <Content extra={extraContent}>{renderContent()}</Content>
//         </PageHeader>
//     );

// }

// export default CourseHeader;