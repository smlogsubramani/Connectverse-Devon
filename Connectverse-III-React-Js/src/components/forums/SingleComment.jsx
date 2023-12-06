import React, { createElement, useState } from 'react';

import { Avatar, Button, Comment, Form, Input, List, Tooltip } from 'antd';
import { FlagFilled, FlagOutlined, LikeFilled, LikeOutlined } from '@ant-design/icons';
import moment from 'moment';


const SingleComment = ({ id, likesc, reportsc, comment, commentedBy }) => {
    const [likes, setLikes] = useState(likesc);
    const [reports, setReports] = useState(reportsc);
    const [action, setAction] = useState(null);

    const like = () => {
        setLikes(1);
        setReports(0);
        setAction('liked');
    };
    
    const report = () => {
        setLikes(0);
        setReports(1);
        setAction('reported');
    };

    const actions = [
        <Tooltip key="comment-basic-like" title="Like">
          <span onClick={like}>
            {createElement(action === 'liked' ? LikeFilled : LikeOutlined)}
            <span className="comment-action">{likes}</span>
          </span>
        </Tooltip>,
        <Tooltip key="comment-basic-dislike" title="Report">
          <span onClick={report}>
            {React.createElement(action === 'reported' ? FlagFilled : FlagOutlined)}
            <span className="comment-action">{reports}</span>
          </span>
        </Tooltip>,
    ];

    return (
        <Comment
            actions={actions}
            author={commentedBy}
            avatar={<Avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />}
            content={
                <p>
                    { comment }
                </p>
            }
            datetime={
                <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                <span>{moment().fromNow()}</span>
                </Tooltip>
            }
      />
    );
};

export default SingleComment;
