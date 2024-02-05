import React from 'react'
import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import SH_Feed from "./SH_Feed"
import { useLocation } from 'react-router-dom';
import "../freezone_page/freezone.css"
import { useState } from 'react';

export default function SH_Page() {
  const location = useLocation();
  const user = location.state?.user;
  const [searchTerm, setSearchTerm] = useState('');
  return (
    <>
    <Topbar user={user} onSearchChange={setSearchTerm}/>
    <div className="homeContainer">
      <Sidebar user={user}/>
      <SH_Feed user={user} searchTerm={searchTerm}/>
    </div>
    </>
  )
}
