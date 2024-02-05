import React from 'react'
import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import LiveChat from "../../components/chat/ChatRoom"
import { useLocation } from 'react-router-dom';

import "../freezone_page/freezone.css"


export default function Chat() {
  const location = useLocation();
  const user = location.state?.user;
  return (
    <>
    <Topbar user={user}/>
    <div className="homeContainer">
      <Sidebar user={user}/>
      <LiveChat/>
    </div>
    </>
  )
}