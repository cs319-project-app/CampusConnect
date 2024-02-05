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
import MoreVertIcon from '@mui/icons-material/MoreVert';
//import SmsIcon from '@mui/icons-material/Sms';
import ForwardToInboxIcon from '@mui/icons-material/ForwardToInbox';
//import FastfoodIcon from '@mui/icons-material/Fastfood';

//import ShareIcon from '@mui/icons-material/Share';
import { CardActionArea } from '@mui/material';
import {useNavigate} from 'react-router-dom'

import "../post/post.css"
import "../card/shcard.css"

//USE STATE IMPORTS
import Modalv2 from '../modal/Modalv2';
import { useState } from "react"  
import { useEffect } from "react";
import axios from 'axios';
export default function SHCard({post, currentUser}) {
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
  const itemName = "ASDSADASDASDSADSADSADASDA"
  const item_age = "CONDITION CONDITION CONDITION"
  const item_price= "100"

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
    
    <Card sx={{ maxWidth: 345 }}>
      
    <CardHeader avatar={<Avatar alt={user?.username} src={user?.userPhotoPath} onClick={() => navigate("/profile", {state: {user: currentUser, clickedUser: user}})} className='postProfileImg'/>}
      /*<Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">R</Avatar>*/
      /*action={<IconButton aria-label="settings"> <MoreVertIcon /> </IconButton>}*/
      
      title={user?.username}
      subheader={post.postDate}
      action={          <IconButton aria-label="settings">
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
    <Modalv2 modal={modal} toggleModal={toggleModal} user={user} post={post}/>
    </CardActionArea>
    
    <div className='item-name'>{(post.itemName+"").length <= 10 ? post.itemName: (post.itemName.substr(0, 10) + "...")}</div>
    
    <CardActions >
      <div className='bottom-card-bar'>
        <IconButton aria-label="add to favorites">
          <ForwardToInboxIcon onClick={() => navigate("/live-chat",{state: {user: currentUser}})}/>
        </IconButton>
        <p>
  {(`${post.itemAge} years old`).length <= 13 
    ? `${post.itemAge} years old` 
    : (`${post.itemAge} years old`.substr(0, 10) + "...")}
</p>
        <p className='price-style'>{(post.itemPrice+"").length <= 4 ? post.itemPrice +"TL": (post.itemPrice.substr(0, 4) + "...TL")}</p>
      </div>
    </CardActions> 
    
  </Card>
  )
}
