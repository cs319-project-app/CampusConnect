import "./modal.css";
import NewComment from "../comment/NewComment"
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useCallback } from 'react';
//postUser is the user who made the post
//user is the user who is logged in
//so comment and like will be done by user
export default function Modal({modal, toggleModal,user,userLoggedIn, post}) {
  const [comments, setComments] = useState(post.comments || []);
  
  const getComments = useCallback(async () => {
    try {
      const response = await axios.get(`http://localhost:8080/v1/comments/post/${post.postId}`);
      setComments(response.data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  }, [post.postId]); 
  useEffect(() => {
    getComments();
  }, [getComments]);

  const [commentText, setCommentText] = useState('');

  const handleInputChange = (e) => {
    setCommentText(e.target.value);
  };

  const handleSendClick = async () => {
    if (!commentText.trim()) return;
    const newComment = {
      commentContent: commentText,
    };
  try {
    const url = `http://localhost:8080/v1/comments/create?postId=${post.postId}&userId=${userLoggedIn.user_id}`;
    console.log(url);
    const response = await axios.post(url, newComment);
    const createdComment = response.data;
    console.log(createdComment)
    setComments(prevComments => [createdComment, ...prevComments]);
    setCommentText(''); 

} catch (error) {
    console.error('Error creating comment:', error);
}
  };

  return (
    <>

      {modal && (
        <div className="modal">
          <div onClick={toggleModal} className="overlay"></div>
          <div className="wrapper">
            <div className="modal-content">
              <div className="postTop">
                <div className="postTopLeft">
                    <img className="postProfileImg" src={post.user.userPhotoPath} alt="" />
                    <span className="postUsername">{post.user.username}</span>
                    <span className="postDate">{post.creationTimestamp}</span> {/* xx/xx/xxxx at xx.xx */}
                </div>
              </div>
              <p className="postDescription">
                {post?.content}
              </p>
              <div className="wrapper-right"> <img className='postImgModal' src={post.imagePath} alt="" />
              <div>
                {comments.map((comment) => (
                <NewComment key={comment.commentId} comment={comment} user={userLoggedIn} />
                ))}
                {/* <NewComment comment={postComment} user={Users[0]}/> */}
                <div className="commentWrite">
                <input value={commentText}
                      onChange={handleInputChange}
                >
                </input>
                <button onClick={handleSendClick}>Send</button>
                </div>
              </div>
              </div>
                <button className="close-modal" onClick={toggleModal}>
                  CLOSE
                </button>
              </div>
            </div>
        </div>
      )}
    </>
  );
}