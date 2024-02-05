import "./sidebar.css"
import {LocalOffer,Tune,ReportProblem,ContactSupport
    ,VolunteerActivism,Radar, ThumbsUpDown,Nightlife} from "@mui/icons-material"
import {useNavigate} from 'react-router-dom'
import MeetingRoomIcon from '@mui/icons-material/MeetingRoom';
import BugReportIcon from '@mui/icons-material/BugReport';
import { useState } from 'react';
import Modal_bugrep from "../modal/Modal_bugrep";
import Modal_idCardReport from "../modal/Modal_idCardReport";
import axios from "axios";

export default function Sidebar({user}) {

    const userId = user.user_id;
  const navigate = useNavigate();

  const [modal, setModal] = useState(false);

    const toggleModal = () => {
        setModal(!modal);
    };

    if(modal) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }

    const [modal2, setModal2] = useState(false);

    const toggleModal2 = () => {
        setModal2(!modal2);
    };

    if(modal2) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }

    const createIdReport = async (newReport) => {
        
        try {
          console.log("Hey");
          console.log(newReport);
          console.log("Hey");
          //CORRECT HERE
          const response = await axios.post(`http://localhost:8080/v1/idCard/post`, newReport, {
            params: { userId }   
          });
        //   setPosts(prevPosts => [response.data, ...prevPosts]);
          console.log(response.data);
        } catch (error) {
          console.error('Error creating post:', error);
        }
        const notificationData = {
            content: `Your ID Card has been reported as lost. Please contact the person who reported it to you. ${newReport.losedIdNo} ${newReport.idLoserName} is the ID number of the lost ID Card.`,
            foundId:newReport.losedIdNo        
        };
        console.log(notificationData);
        try{
            const response = await axios.post(`http://localhost:8080/v1/notifications/cardReport`, notificationData);
              console.log(response.data);
        }catch (error) {
            console.error('Error creating notification:', error);
          }
    
      };
  
      const handleIdReport = (newReport) => {
        createIdReport(newReport);
      };


  return (
    <>
    <Modal_bugrep modal={modal} toggleModal={toggleModal}/>
    <Modal_idCardReport modal={modal2} toggleModal={toggleModal2} user={user} onIdReport={handleIdReport}/>
    <div className = "sidebar">
        <div className="sidebarWrapper">
            <ul className="sidebarList">
                <li className="sidebarListItem">
                    <Radar className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={() => navigate("/lost-found",{state: {user: user}})}>Lost & Found</button></span>
                </li>
                <li className="sidebarListItem">
                    <LocalOffer className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button class="nav-btn" onClick={() => navigate("/second-hand",{state: {user: user}})}>Second-Hand Sales</button></span>
                </li>
                <li className="sidebarListItem">
                    <ThumbsUpDown className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={() => navigate("/borrows",{state: {user: user}})}>Borrow</button></span>
                </li>
                <li className="sidebarListItem">
                    <VolunteerActivism className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={() => navigate("/donations",{state: {user: user}})}>Donations</button></span>
                </li>
                <li className="sidebarListItem">
                    <Nightlife className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={() => navigate("/freezone",{state: {user: user}})}>FreeZone</button></span>
                </li>
            </ul>
            <hr className="sidebarHr"/>
            <li className="sidebarListItem">
                    <ReportProblem className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={toggleModal2}>ID Card Report</button></span>
            </li>
            <hr className="sidebarHr"/>
            <ul className="sidebarList">
                <li className="sidebarListItem">
                    <Tune className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn">Settings</button></span>
                </li>

                <li className="sidebarListItem">
                    <BugReportIcon className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={toggleModal}>Bug Report</button></span>
                </li>
            </ul>
            <hr className="sidebarHr"/>
            <li className="sidebarListItem">
                    <MeetingRoomIcon className="sidebarIcon"/>
                    <span className="sidebarListItemText"><button className="nav-btn" onClick={() => {navigate('/', [])}}>Log Out</button></span>
            </li>
        </div>
    </div>
    </>
  )
}
