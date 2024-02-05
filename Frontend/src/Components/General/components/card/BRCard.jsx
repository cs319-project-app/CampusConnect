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
import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import axios from 'axios';
import { useEffect } from 'react';
import "../post/post.css"
import {useNavigate} from 'react-router-dom'

//USE STATE IMPORTS
import Modal_LF from '../modal/Modal_donation';
import { useState } from "react"

export default function BRCard({post, currentUser}) {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  console.log("Post:"+post.content);
  const [modal, setModal] = useState(false);
  useEffect(() => {
    const fetchUser = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/v1/user/${post.userIdValue}`);
            setUser(response.data);
        } catch (err) {
            console.error('Error fetching user:', err);
        }
    };

    fetchUser();
}, [post.userIdValue]);
  const toggleModal = () => {
      setModal(!modal);
  };

  if(modal) {
      document.body.classList.add('active-modal')
  } else {
      document.body.classList.remove('active-modal')
  }

  return (
    
    <Card sx={{ maxWidth: 345 }}>
      
    <CardHeader avatar={<Avatar alt={user?.username} src={user?.userPhotoPath} onClick={() => navigate("/profile", {state: {user: currentUser, clickedUser: user}})} className='postProfileImg'/>}
      /*<Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">R</Avatar>*/
      /*action={<IconButton aria-label="settings"> <MoreVertIcon /> </IconButton>}*/
      title={user?.username}
      subheader={post?.postDate}
      action={<IconButton aria-label="settings">
      <MoreVertIcon />
      </IconButton>}
    />
    
    <CardActionArea onClick={toggleModal}>
      <CardMedia
        component="img"
        height="194"
        image={post?.imagePath}
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
        <ForwardToInboxIcon onClick={() => navigate("/live-chat",{state: {user: user}})}/>
      </IconButton>
      </div>
      <div className="borrow-wrap">
        <p>{post?.takenDate}</p>
        <div className="arrow-loc"><KeyboardDoubleArrowDownIcon/></div>
        <p>{post?.expectedReturnDate}</p>
      </div>
    </CardActions> 
    
  </Card>
  )
}
