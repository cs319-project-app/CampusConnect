import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './newComment.css';
import CommentReply from './CommentReply'; // Create a separate CommentReply component for handling replies
import axios from 'axios';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
const Comment = ({ comment ,user}) => {
  const [likes, setLikes] = useState(comment.likes || 0);
  const [isLiked, setIsLiked] = useState(false);
  const [showReplies, setShowReplies] = useState(false);
  const [replies, setReplies] = useState([]);
  
  const handleLike = () => {
    if (!isLiked) {

      setLikes(likes + 1);
    } else {
      setLikes(likes - 1);
    }
    setIsLiked(!isLiked);
  };

  const handleToggleReplies = () => {
    setShowReplies(!showReplies);
  };

  const handleAddReply = async (replyText) => {
    const newReply = {
      commentContent: replyText,
      // Add other necessary fields if required
    };
  
    try {
      const url = `http://localhost:8080/v1/comments/${comment.commentId}/replies/${user.user_id}`;
      const response = await axios.post(url, newReply);
      const createdReply = response.data;
  
      setReplies([...replies, createdReply]); 
    } catch (error) {
      console.error('Error adding reply:', error);
    }
  };
  

  return (
    <div className="comment">
      <img src={comment?.user?.userPhotoPath} alt="Profile" className="profile-image" />
      <div className="comment-content">
        <div className="comment-header">
          <span className="username">{comment?.user?.username+" "}</span>
          <span className="date">{comment?.creationTimestamp}</span>
        </div>
        <p className="comment-text">{comment?.commentContent}</p>
        <div className="comment-actions">
          <Stack direction="row" spacing={1}>
          <Button variant="contained" onClick={handleLike} color="info" style={{ width: "20px", height: "25px",}} className={isLiked ? 'liked' : ''}>
            {isLiked ? 'Unlike' : 'Like'}</Button>
          <Button variant="contained" onClick={handleToggleReplies} color="secondary" style={{ width: "150px", height: "25px",}}> {showReplies ? 'Hide Replies' : 'View Replies'}</Button>
          </Stack>
        
        </div>
        {showReplies && (
          <div className="replies">
            {replies.map((reply) => (
              <CommentReply key={reply.id} reply={reply} />
            ))}
            {/* Add a reply input form */}
            <CommentReplyForm onAddReply={handleAddReply} />
          </div>
        )}
      </div>
    </div>
  );
};

Comment.propTypes = {
  comment: PropTypes.shape({
    id: PropTypes.number.isRequired,
    username: PropTypes.string.isRequired,
    profileImage: PropTypes.string.isRequired,
    date: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired,
    likes: PropTypes.number,
  }).isRequired,
};

const CommentReplyForm = ({ onAddReply }) => {
  const [replyText, setReplyText] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (replyText.trim() !== '') {
      onAddReply(replyText);
      setReplyText('');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="reply-form">
      <textarea
        value={replyText}
        onChange={(e) => setReplyText(e.target.value)}
        placeholder="Reply to this comment..."
      />
      <button type="submit">Reply</button>
    </form>
  );
};

CommentReplyForm.propTypes = {
  onAddReply: PropTypes.func.isRequired,
};

export default Comment;
