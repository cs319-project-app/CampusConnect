import './App.css';
import LoginSignup from './Components/LoginSignup/LoginSignup';
import {MainPage} from './Components/Dashboard/MainPage'
import { Route, Routes } from 'react-router-dom';
import { ForgotPasswordPage } from './Components/ForgotPasswordPage/ForgotPasswordPage';
import Home from "./Components/General/pages/freezone_page/Freezone";
import LS from "./Components/General/pages/lost&found_page/LS_Page";
import SH from "./Components/General/pages/second_hand_page/SH_Page";
import DON from "./Components/General/pages/donation_page/Donation";
import BOR from "./Components/General/pages/borrow_page/Borrow";
import PRO from "./Components/General/pages/profile_page/Profile_Feed";
import Upbtn from "./Components/General/components/scroll_up/BackToTopBottomButton";
import LiveChat from "./Components/General/pages/chat_page/Chat"
import WEL from "./Components/General/pages/welcome_page/Welcome2";
function App() {
  return (
    <>
    <Upbtn/>
    <Routes>
      <Route path = "/" element = { <LoginSignup/> } />
      <Route path = "/forgotpassword" element = { <ForgotPasswordPage/>} />
      <Route path = "/dashboard" element = { <MainPage/>} />
      <Route path = "/freezone" element={<Home/>}/>
      <Route path = "/borrows" element={<BOR/>}/>
      <Route path = "/donations" element={<DON/>}/>
      <Route path = "/lost-found" element={<LS/>}/>
      <Route path = "/second-hand" element={<SH/>}/>
      <Route path = "/profile" element={<PRO/>}/>
      <Route path = "/live-chat" element={<LiveChat/>}/>
      <Route path = "/welcome" element={<WEL/>}/>
    </Routes>
    </>
  );
}

export default App;
