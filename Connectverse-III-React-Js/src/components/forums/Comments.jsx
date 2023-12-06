import { FlagFilled, FlagOutlined, LikeFilled, LikeOutlined } from '@ant-design/icons';
import { Avatar, Comment, Tooltip } from 'antd';
import moment from 'moment';
import React, { createElement, useState } from 'react';

import Reply from './Reply';


const Comments = ({ id, comment, likesc, reportsc, commentedBy, replies }) => {

    const [likes, setLikes] = useState(likesc);
    const [reports, setReports] = useState(reportsc);
    const [action, setAction] = useState(null);

    const [isReplyHidden, setReplyHidden] = useState(true);

    const like = () => {
        setLikes(1);
        setReports(0);
        setAction('liked');
    };

    const report = () => {
        setLikes(0);
        setReports(1);
        setAction('disliked');
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
            {React.createElement(action === 'disliked' ? FlagFilled : FlagOutlined)}
            <span className="comment-action">{reports}</span>
        </span>
        </Tooltip>,
        <span key="comment-basic-reply-to" onClick={() => { setReplyHidden(!isReplyHidden) }}>Reply to</span>,
    ];

    return (
        <div>
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
            <div hidden={isReplyHidden}>
                <Reply replies={replies} />
            </div>
        </div>
    );
};

export default Comments;
