import React, { createElement, useState } from 'react';

import { Avatar, Button, Comment, Form, Input, List, Tooltip } from 'antd';
import { FlagFilled, FlagOutlined, LikeFilled, LikeOutlined } from '@ant-design/icons';
import moment from 'moment';
import SingleComment from './SingleComment';
const { TextArea } = Input;

const Editor = ({ onChange, onSubmit, submitting, value }) => (
    <>
      <Form.Item>
        <TextArea rows={4} onChange={onChange} value={value} />
      </Form.Item>
      <Form.Item>
        <Button htmlType="submit" loading={submitting} onClick={onSubmit} type="primary">
          Add Comment
        </Button>
      </Form.Item>
    </>
);

const Reply = ({ replies }) => {

  const [likes, setLikes] = useState(0);
    const [reports, setReports] = useState(0);
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

    const [comments, setComments] = useState([]);
    const [submitting, setSubmitting] = useState(false);
    const [value, setValue] = useState('');

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

    const CommentList = ({ comments }) => (
        <List
          dataSource={comments}
          header={`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`}
          itemLayout="horizontal"
          renderItem={(props) => <Comment actions={actions} {...props}  />}
        />
    );

  const handleSubmit = () => {
    if (!value) return;
    setSubmitting(true);
    setTimeout(() => {
      setSubmitting(false);
      setValue('');
      setComments([
        ...comments,
        {
          author: 'Han Solo',
          avatar: 'https://joeschmoe.io/api/v1/random',
          content: <p>{value}</p>,
          datetime: moment().fromNow(),
        },
      ]);
    }, 1000);
  };

  const handleChange = (e) => {
    setValue(e.target.value);
  };

  return (
    <>
      {comments.length > 0 && <CommentList comments={comments} />}
      {
        replies.map(reply => (
          <SingleComment id={reply.id} likesc={reply.likes} reportsc={reply.reports} comment={reply.reply} commentedBy={reply.commentedBy}/>
        ))
      }
      <Comment
        avatar={<Avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />}
        content={
          <Editor
            onChange={handleChange}
            onSubmit={handleSubmit}
            submitting={submitting}
            value={value}
          />
        }
      />
    </>
  );
};

export default Reply;
