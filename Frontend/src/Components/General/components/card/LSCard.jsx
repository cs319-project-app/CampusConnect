//CARD IMPORTS
import * as React from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
//import SmsIcon from '@mui/icons-material/Sms';
import ForwardToInboxIcon from '@mui/icons-material/ForwardToInbox';
//import ShareIcon from '@mui/icons-material/Share';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { CardActionArea } from '@mui/material';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import {useNavigate} from 'react-router-dom'

import "../post/post.css"
import {useEffect} from 'react';
//USE STATE IMPORTS
//import Modalv2 from '../modal/Modalv2';
import Modal_LF from '../modal/Modal_donation';
import ModalLFAdd from '../modal/ModalLFAdd';
import { useState } from "react"
import axios from 'axios';

export default function LSCard({post, currentUser,refreshPosts}) {
  const [updateModal, setUpdateModal] = useState(false);
  const navigate = useNavigate();

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
}, [post.userIdValue]);
  
  const [modal, setModal] = useState(false);

  const toggleModal = () => {
      setModal(!modal);
  };

  if(modal) {
      document.body.classList.add('active-modal')
  } else {
      document.body.classList.remove('active-modal')
  }
  console.log(post);
  const [anchorEl, setAnchorEl] = useState(null);

const handleMenuClick = (event) => {
  setAnchorEl(event.currentTarget);
};

const handleMenuClose = () => {
  setAnchorEl(null);
};
const handleNewUpdateItemShare = (newItem) => {
  handleUpdateItem(newItem);
};
const handleDeletePost = async () => {
  try {
    const response = await axios.delete(`http://localhost:8080/v1/lost-and-found/${post.itemId}`);
    console.log("Post deleted:", post);
    if (response.status === 200) {
      console.log('Post deleted successfully');
      refreshPosts();
    }
  } catch (error) {
    console.error('Error deleting the post:', error);
  }
  handleMenuClose();
};

const handleUpdateItem= async (updatedItem) => {
  try {
    const response = await axios.put(`http://localhost:8080/v1/lost-and-found/update/${post.itemId}`, updatedItem);
    console.log("Post updated:", post);
    if (response.status === 200) {
      console.log('Post updated successfully');
      refreshPosts();
    }
  } catch (error) {
    console.error('Error updating the post:', error);
  }
  handleMenuClose();
}
const handleUpdatePost = (itemId) => {
  handleOpenUpdateModal();
  handleMenuClose();
};
const handleOpenUpdateModal = () => {
  setUpdateModal(true);
};

const handleCloseUpdateModal = () => {
  setUpdateModal(false);
};
if(updateModal) {
  document.body.classList.add('active-modal')
} else {
  document.body.classList.remove('active-modal')
}
  return (
    
    <Card sx={{ maxWidth: 345 }}>
      
    <CardHeader avatar={<Avatar alt={user?.user_id} src={user?.userPhotoPath} onClick={() => navigate("/profile", {state: {user: currentUser, clickedUser: user}})} className='postProfileImg'/>}
      /*<Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">R</Avatar>*/
      /*action={<IconButton aria-label="settings"> <MoreVertIcon /> </IconButton>}*/
      
      title={user?.username}
      subheader={post.postDate}
      body={post.location}
      action=          {<IconButton
        aria-label="settings"
        aria-controls="post-menu"
        aria-haspopup="true"
        onClick={handleMenuClick}
      >
        <MoreVertIcon />
      </IconButton>}
      
    />
    

    <CardActionArea onClick={toggleModal}>
      <CardMedia
        component="img"
        height="194"
        image={post.imagePath}
        alt=""
      />
    
    <CardContent>
      <Typography variant="body2" color="text.secondary">
      {(''+post?.description).length <= 80 ? post?.description: (post?.description.substr(0, 80) + "...")}
      </Typography>
    </CardContent>
    <Modal_LF modal={modal} toggleModal={toggleModal} user={user} post={post}/>
    </CardActionArea>

    <div className='item-name'>{(post.itemName+"").length <= 10 ? post.itemName: (post.itemName.substr(0, 10) + "...")}</div>
    
    <CardActions disableSpacing>
    <div className='bottom-card-bar'>
      <IconButton aria-label="add to favorites" >
        <ForwardToInboxIcon onClick={() => navigate("/live-chat",{state: {user: currentUser}})}/>
      </IconButton>
      </div>
      <div className="bottomwrap">
        <LocationOnIcon />
        <p className='location-style'>{(post.location+"").length <= 15 ? post.location: (post.location.substr(0, 15) + "...")}</p>
      </div>
    </CardActions> 
    {currentUser?.user_id === post.userIdValue && (
        <Menu
          id="post-menu"
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
          transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        >
          <MenuItem onClick={() => handleDeletePost(post.itemId)}>Delete Post</MenuItem>
          <MenuItem onClick={() => handleUpdatePost(post.itemId)}>Update Post</MenuItem>
        </Menu>
      )}
      {updateModal && (
                <ModalLFAdd
                    modal={updateModal}
                    toggleModal={handleCloseUpdateModal}
                    user={user}
                    post={post}
                    onItemShare={handleNewUpdateItemShare}
                />
            )}
  </Card>
  )
}