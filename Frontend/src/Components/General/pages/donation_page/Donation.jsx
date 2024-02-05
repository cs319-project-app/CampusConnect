import React from 'react'
import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import DonationFeed from "../../pages/donation_page/DonationFeed"
import { useLocation } from 'react-router-dom';
import "../freezone_page/freezone.css"
import { useState } from "react";

export default function SH_Page() {
  const location = useLocation();
  const [searchTerm, setSearchTerm] = useState('');
  const user = location.state?.user;
  return (
    <>
    <Topbar user={user} onSearchChange={setSearchTerm}/>
    <div className="homeContainer">
      <Sidebar user={user}/>
      <DonationFeed user={user}  searchTerm={searchTerm}/>
    </div>
    </>
  )
}