import './topbar.css'
import { Search, Person, Chat, Notifications, BedroomBabyRounded } from "@mui/icons-material"
import NotificationDropdown from '../topbar/NotificationDropdown'
import {useNavigate} from 'react-router-dom'
import { useState, useEffect } from 'react';
import axios from 'axios';

export default function Topbar({user, onSearchChange}) {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState(''); 
  const [notifications, setNotifications] = useState([]);
  console.log(user);
  useEffect(() => {
    const url = `http://localhost:8080/v1/notifications/user/${user.user_id}`;
    console.log("user:"+user.userPhotoPath);
    axios.get(url)
        .then(response => {
            setNotifications(response.data);
        })
        .catch(error => {
            console.error('Error fetching notifications:', error);
        });
}, [user.user_id,user.userPhotoPath]);
const handleSearchChange = (event) => {
  onSearchChange(event.target.value);
  setSearchTerm(event.target.value);
};
const handleSearchSubmit = () => {
  console.log('Searching for:', searchTerm);
  // For example, navigate to a search results page
  // navigate('/search-results', { state: { searchTerm } });
};
  return (
    <div className="topbarContainer">
        <div className="topbarLeft">
        <span className="logo" onClick={() => navigate("/welcome", {state: {user: user,}})}>CampusConnect</span>
        </div>
        <div className="topbarCenter">
          <div className="searchbar">
            <Search className='searchIcon'/>
            <input
              placeholder='Search for things'
              className="searchInput"
              value={searchTerm}
              onChange={handleSearchChange}
              onKeyDown={(e) => e.key === 'Enter' && handleSearchSubmit()}
            />
          </div>
        </div>
        <div className="topbarRight">
          <div className="topbarLinks">
              {/* PLUS SIGN POST ATMA */}
          </div>
          <div className="topbarIcons">

            <div className="topbarIconItem">
              <Chat onClick={() => navigate("/live-chat",{state: {user: user}})}/>
            </div>
            {/* <div className="topbarIconItem">
              <Notifications/>
              <span className="topbarIconBadge">1</span>
            </div> */}
            <div className="topbarIconItem">
              <NotificationDropdown user={user} notifications={notifications}/>
              <span className="topbarIconBadge">{notifications.length}</span>
            </div>
          </div>
          <img src={user.userPhotoPath} alt="" className="topbarImg" onClick={() => navigate("/profile", {state: {user: user, clickedUser: user}})}/>
        </div>
    </div>
  )
}