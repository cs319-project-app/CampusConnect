import Topbar from "../../components/topbar/Topbar"
import Sidebar from "../../components/sidebar/Sidebar"
import Feed from "../../components/feed/Feed"
import Rightbar from "../../components/rightbar/Rightbar"
import { useLocation } from 'react-router-dom';
import "./freezone.css"
import { useState } from "react";

export default function Freezone(){
  const location = useLocation();
  const [searchTerm, setSearchTerm] = useState('');
  const user = location.state?.user;
  return (
    <>
    
    <Topbar user={user} onSearchChange={setSearchTerm} />
    <div className="homeContainer">
      <Sidebar user={user}/>
      <Feed user={user} searchTerm={searchTerm} />
      <Rightbar/>
    </div>
    
    </> //for calling multiple components
  )
}
