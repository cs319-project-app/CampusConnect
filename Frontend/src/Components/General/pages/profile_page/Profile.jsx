import "./profile.css";
import FacebookTwoToneIcon from "@mui/icons-material/FacebookTwoTone";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import InstagramIcon from "@mui/icons-material/Instagram";
import PinterestIcon from "@mui/icons-material/Pinterest";
import TwitterIcon from "@mui/icons-material/Twitter";
import PlaceIcon from "@mui/icons-material/Place";
import LanguageIcon from "@mui/icons-material/Language";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import React from "react";
import axios from "axios";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
const Profile = () => {
  const location = useLocation();
  const user = location.state?.user;
  const clickedUser = location.state?.clickedUser;
  const navigate = useNavigate();
  
  const [userProfilePic, setUserProfilePic] = useState(clickedUser.userPhotoPath);
  useEffect(() => {
    setUserProfilePic(clickedUser.userPhotoPath);
  });
  const fileInputRef = React.createRef();
  const handleFileChange = async (event) => {
    
    const file = event.target.files[0];
    const formData = new FormData();
    formData.append('file', file);
    // Create a JSON object with the file path and placeholders for username and password
    const fileInfo = {
        profilePath: `./assets/person/${file.name}`, // Assuming file.name gives the correct path
        username: clickedUser.username, // Placeholder for username
        password: clickedUser.password  // Placeholder for password
    };

    try {
        const response = await axios.put(`http://localhost:8080/v1/user/${clickedUser.user_id}`, 
            JSON.stringify(fileInfo), {
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log(response.data);
        // Update userPhotoPath with the new path
        clickedUser.userPhotoPath = fileInfo.profilePath;
        setUserProfilePic(fileInfo.profilePath);
    } catch (error) {
        console.error('Error uploading profile picture:', error);
    }
};


  return (
    <div className="profile">
       <div className="images">
        <img src={userProfilePic} alt={`${clickedUser.username}'s profile`} className="cover" />
        {/* <input type="file" onChange={handleFileChange} style={{ display: 'none' }} ref={fileInputRef} />
        <button onClick={() => fileInputRef.current.click()}>Change Profile Picture</button> */}
        {user.user_id === clickedUser.user_id && (
          <>
            <input type="file" onChange={handleFileChange} style={{ display: 'none' }} ref={fileInputRef} />
            <button onClick={() => fileInputRef.current.click()}>Change Profile Picture</button>
          </>
        )}
        <img
          src={userProfilePic}
          alt=""
          className="profilePic"
        />
      </div>
        
      <div className="profileContainer">
        <div className="uInfo">
          <div className="left">
            <a href="http://facebook.com">
              <FacebookTwoToneIcon fontSize="large" />
            </a>
            <a href="http://instagram.com">
              <InstagramIcon fontSize="large" />
            </a>
            <a href="http://twitter.com">
              <TwitterIcon fontSize="large" />
            </a>
            <a href="http://linkedin.com">
              <LinkedInIcon fontSize="large" />
            </a>
            <a href="http://pinterest.com">
              <PinterestIcon fontSize="large" />
            </a>
          </div>
          <div className="center">
            <span>{clickedUser.username}</span>
            <div className="info">
              <div className="item">
                <PlaceIcon />
                <span>TR</span>
              </div>
              <div className="item">
                <LanguageIcon />
                <a href="https://github.com/" target="_blank">github</a>
              </div>
            </div>
            <span>{clickedUser.name + " "+clickedUser.surname}</span>
          </div>
          <div className="right">
            <EmailOutlinedIcon onClick={() => navigate("/live-chat",{state: {user: user}})} style={{cursor: "pointer"}}/>
            <MoreVertIcon style={{cursor: "pointer"}}/>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;