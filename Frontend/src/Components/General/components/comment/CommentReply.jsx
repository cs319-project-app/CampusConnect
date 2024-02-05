import React from 'react';
import PropTypes from 'prop-types';
import { useState } from 'react';
import { useEffect } from 'react';
import axios from 'axios';
const CommentReply = ({ reply }) => {
  const [user, setUser] = useState(null);
useEffect(() => {
        
    const fetchUser = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/v1/user/${reply.userIdValue}`);
            setUser(response.data);
            console.log("User:"+response.data.userPhotoPath);
        } catch (err) {
            console.error('Error fetching user:', err);
        }
    };

    if (reply.userIdValue) {
        fetchUser();
    }
}, [reply.userIdValue]);
  return (
    <div className="comment-reply">
      <img src={user?.userPhotoPath} alt="Profile" className="profile-image" />
      <div className="reply-content">
        <div className="reply-header">
          <span className="username">{user?.username+" "}</span>
          <span className="date">{reply?.creationTimestamp}</span>
        </div>
        <p className="reply-text">{reply?.commentContent}</p>
      </div>
    </div>
  );
};

CommentReply.propTypes = {
  reply: PropTypes.shape({
    id: PropTypes.number.isRequired,
    username: PropTypes.string.isRequired,
    profileImage: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired,
  }).isRequired,
};

export default CommentReply;
