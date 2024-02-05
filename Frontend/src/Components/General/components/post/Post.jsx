import "./post.css"
import { MoreVert } from "@mui/icons-material"
import { useState } from "react"
import Modal from '../modal/Modal'
import axios from 'axios';
import React from 'react'
import {useNavigate} from 'react-router-dom'
import { useEffect } from "react";
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
export default function Post({post,userLoggedIn,refreshPosts}) {
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const navigate = useNavigate();
    const [likes, setLikes] = useState(post.likedByUsers.length);
    const [isLiked, setIsLiked] = useState(false);
    const [user, setUser] = useState(null);
    useEffect(() => {
        
        const fetchUser = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/v1/user/${post.userIdValue}`);
                setUser(response.data);
                console.log("User:"+response.data.userPhotoPath);
            } catch (err) {
                console.error('Error fetching user:', err);
            }
        };

        if (post.userIdValue) {
            fetchUser();
        }
    }, []);
    const userId = userLoggedIn.user_id;
    const likeHandler = async () => {
        
        try {
            const response = await axios.post(`http://localhost:8080/v1/posts/${post.postId}/like`, null, {
                params: { userId }
            });
            setIsLiked(response.data); // Toggle like state
            console.log("response"+response.data);
            if(response.data) {
                setLikes(likes + 1);
            }
            else {
                if(likes > 0) {
                    setLikes(likes - 1);
                }
                else {
                    setLikes(0);
                }
            }
            
            
        } catch (error) {
            console.error('Error liking post:', error);
        }
    };
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleUpdate = () => {
        const updatePost = async (postId, updatedPostData) => {
            try {
              const response = await axios.put(`http://localhost:8080/your-endpoint/${postId}`, updatedPostData);
              console.log('Post updated successfully:', response.data);
              return response.data; // returning the updated post
            } catch (error) {
              console.error('Error updating post:', error);
              // Handle error appropriately
            }
          };
        handleClose();
    };
    const handleDelete = async() => {
        try {
            const response = await axios.delete(`http://localhost:8080/v1/posts/${post.postId}`);
            console.log("Post deleted:", post);
            if (response.status === 200) {
              console.log('Post deleted successfully');
              refreshPosts();
            }
          } catch (error) {
            console.error('Error deleting the post:', error);
          }
        handleClose();
    };
    useEffect(() => {
        const checkLikeStatus = async () => {
          try {
            const response = await axios.get(`http://localhost:8080/v1/posts/isLike/${post.postId}`, {
              params: { userId: userLoggedIn.user_id }
            });
            setIsLiked(response.data); 
          } catch (error) {
            console.error('Error checking like status:', error);
          }
        };
        checkLikeStatus();
      }, [post, userLoggedIn.user_id]);
      

    const [modal, setModal] = useState(false);

    const toggleModal = () => {
        setModal(!modal);
    };

    if(modal) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }
  return (
    <div className="post">
        <div className="postWrapper">
            <div className="postTop">
                <div className="postTopLeft">

                    <img className="postProfileImg" src={user?.userPhotoPath} alt="" onClick={() => navigate("/profile", {state: {user: userLoggedIn, clickedUser: user}})} />
                    <span className="postUsername" onClick={() => navigate("/profile", {state: {user: userLoggedIn, clickedUser: user}})}>{user?.username}</span>

                    <span className="postDate">{post.creationTimestamp}</span>
 
                </div>
                <div className="postTopRight">
                <MoreVert onClick={handleClick} />
                {userLoggedIn.user_id === post.userIdValue && (
                    <Menu
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                        MenuListProps={{
                            'aria-labelledby': 'basic-button',
                        }}
                    >
                        <MenuItem onClick={handleUpdate}>Update</MenuItem>
                        <MenuItem onClick={handleDelete}>Delete</MenuItem>
                    </Menu>
                )}
                </div>
            </div>
            <div className="postCenter">
                <span className="postText">{post.content}</span>
                <img className="postImg" src={post.imagePath} alt="" />
            </div>
            <div className="postBottom">
                <div className="postBottomLeft">
                    <span className="postCommentText" onClick={toggleModal}> comments</span>
                    <Modal modal={modal} toggleModal={toggleModal} user={user} userLoggedIn={userLoggedIn} post={post}/>
                </div>
                <div className="postBottomRight">
            <span onClick={likeHandler}>
                {isLiked ? <FavoriteIcon style={{ color: 'red' }} /> : <FavoriteBorderIcon />}
                </span>
             <span className="postLikeCounter">{"  "+likes} people</span>
</div>
            </div>
        </div>
    </div>
  )
}
