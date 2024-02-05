import React from 'react'
import {LoginRegisterHeader} from '../LoginSignup/LoginRegisterHeader'
import { useState } from 'react'
import axios from 'axios';
import { useLocation,useNavigate} from 'react-router-dom';
export const MainPage = () => {
  const navigate = useNavigate();
  const [action] = useState("MAIN MENU :)");

  const location = useLocation();
  const user = location.state?.user;
  console.log(user);
  
  const handleClick = async () => {
    console.log("Attempting to delete user with ID:", user.user_id);
    try {
      const response = await axios.delete(`http://localhost:8080/v1/user/${user.user_id}`);
      if(response.status === 200){
        console.log('Data sent successfully');
        navigate("/");
      }
    } catch (error) {
      console.error('There was an error sending the data:', error);
    }
  };

  const handleClickV2 = async () => {
   navigate("/welcome",{state: {user: user}});
  };
  
  return <>

    <div className='container'>
    <LoginRegisterHeader action={action} />

      <div className="submit-container">
        <button className='submit' onClick={handleClick}> DELETE ACCOUNT! </button>
      </div>
      <div className="submit-container">
        <button className='submit' onClick={handleClickV2}> GO HOMEPAGE </button>
      </div>
    </div>
  </>
  }
