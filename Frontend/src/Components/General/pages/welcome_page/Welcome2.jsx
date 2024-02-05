import React from 'react'
import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import WelcomeFeed from './WelcomeFeed'

import "../freezone_page/freezone.css"

import { useLocation } from 'react-router-dom';
import { useState } from 'react';


export default function Welcome2() {
  const location = useLocation();
  const user = location.state?.user;
  const [searchTerm, setSearchTerm] = useState('');
  return (
    <>
    <Topbar user={user} onSearchChange={setSearchTerm}/>
    <div className="homeContainer">
      <Sidebar user={user}/>
      <WelcomeFeed/>
    </div>
    </>
  )
}