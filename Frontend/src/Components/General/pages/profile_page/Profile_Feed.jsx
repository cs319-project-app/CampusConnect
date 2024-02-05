import React from 'react'
import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import Profile from './Profile'
import { useLocation } from 'react-router-dom';
import "../freezone_page/freezone.css"


export default function Profile_Feed() {
  const location = useLocation();
  const user = location.state?.user;
  return (
    <>
    <Topbar user={user}/>
    <div className="homeContainer">
      <Sidebar user={user}/>
      <Profile user={user} clickedUser={user}/>
    </div>

    </>
  )
}
