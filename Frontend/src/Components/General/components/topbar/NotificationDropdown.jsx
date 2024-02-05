import './NotificationDropdown.css'; // You can create a CSS file for styling
import { Notifications } from "@mui/icons-material"
import axios from 'axios';
import React, { useState, useRef, useEffect } from 'react';

const NotificationDropdown = ({user}) => {
  const [notifications, setNotifications] = useState([]);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
 useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/notifications/user/${user.user_id}`);
        
        if (response.status === 204) {
          setNotifications([]);
      }else {
        setNotifications(response.data);
    } 
      } catch (error) {
        console.error('Error fetching notifications:', error);
      }
    };

    fetchNotifications();
  }, []); 

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  // Add a new ref for the notification
  const notificationRef = useRef(null);
  const notificationContainerRef = useRef(null);

  useEffect(() => {
   const handleClickOutside = (event) => {

     const isNotificationSpanClicked = event.target.closest('.notification-icon') !== null;

     // Check if the clicked element is the emoji picker or its child
     const isNotificationClicked = notificationRef.current && notificationRef.current.contains(event.target);
     // Check if the clicked element is within the emoji picker container
     const isNotificationContainerClicked = notificationContainerRef.current && notificationContainerRef.current.contains(event.target);

     if (!isNotificationClicked && !isNotificationSpanClicked && !isNotificationContainerClicked) {
       // Clicked outside the tag input, emoji picker, and their children, close them
       setIsDropdownOpen(false);
     }
   };

   // Add event listener when the component mounts
   document.addEventListener('mousedown', handleClickOutside);

   // Clean up the event listener when the component unmounts
   return () => {
     document.removeEventListener('mousedown', handleClickOutside);
   };
 }, [notificationRef]);

  return (
    <div className="notification-dropdown">
      <div className="notification-icon" onClick={toggleDropdown}>
        <span><Notifications/></span> {/* You can use your notification icon here */}
      </div>
      <div ref={notificationContainerRef}>
      {isDropdownOpen && (
        <div className="dropdown-content" innerRef={notificationRef}>
          {notifications.map((notificationValue) => (
            <div key={notificationValue.notificationId} className="notification-item">
              {notificationValue.content}
            </div>
          ))}
          {notifications.length === 0 && (
            <div className="no-notifications">No new notifications</div>
          )}
        </div>
      )}
      </div>
    </div>
  );
};

export default NotificationDropdown;
