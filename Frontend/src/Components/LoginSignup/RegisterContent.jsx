import user_icon from '../Assets/person.png'
import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'
import id_icon from '../Assets/idIcon.png'
import { useState } from 'react'
import axios from 'axios';
import {  useLocation,useNavigate} from 'react-router-dom';
import { Link } from 'react-router-dom';

export const RegisterContent = () => {
    const location = useLocation();
    const bool = location.state?.bool;
    const navigate = useNavigate();
    const [username, setNickname] = useState();// '' or delete placeholder ?
    const [name, setName] = useState();
    const [surname, setSurname] = useState();
    const [bilkentID, setID] = useState();
    const [bilkentMail, setMail] = useState();
    const [password, setPW] = useState();

    const handleSubmit = async(e) => {
        e.preventDefault();
        const infos = {username,name,surname,password,bilkentMail,bilkentID};
        try {
                const response = await axios.post('http://localhost:8080/v1/user/create', infos, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                
                if (response.status === 201) {
                    const user = response.data;
                    console.log('Data sent successfully');
                    navigate("/dashboard",{state: {user: user}});
                } else {
                    console.error('Failed to send data:', response.statusText);
                }
            } catch (error) {
                console.error('There was an error sending the data:', error);
            }
    }

    return <>
        <form onSubmit={handleSubmit}>
        <div className="input">
            
                <img src={user_icon} alt="" />
                <input type="text" placeholder='Username'
                 required 
                 value = {username}
                 onChange={(e) => setNickname(e.target.value)}
                />
            
        </div>
        <div className="input">
            
                <img src={user_icon} alt="" />
                <input type="text" placeholder='Name'
                 required 
                 value = {name}
                 onChange={(e) => setName(e.target.value)}
                />
            
        </div>
        <div className="input">
            
                <img src={user_icon} alt="" />
                <input type="text" placeholder='Surname'
                 required 
                 value = {surname}
                 onChange={(e) => setSurname(e.target.value)}
                />
            
        </div>
        <div className="input">
            
                <img src={id_icon} alt="" />
                <input type="BilkentID" placeholder='Bilkent ID' required
                value = {bilkentID}
                onChange={(e) => setID(e.target.value)}/>
            
        </div>

        <div className="input">
            
                <img src={email_icon} alt="" />
                <input type="email" placeholder='Bilkent Mail' required
                value = {bilkentMail}
                onChange={(e) => setMail(e.target.value)}/>
            
        </div>

        <div className="input">
            
                <img src={password_icon} alt="" />
                <input type="password" placeholder='Password' required
                value = {password}
                onChange={(e) => setPW(e.target.value)}/>
            
        </div>

        <div className="submit-container">
            <button className='submit'> Register! </button>
        </div>

        {/*
        <p>{nickname}</p>
        <p>{bilkentid}</p>
        <p>{password}</p>
        <p>{email}</p>
        */}
        </form>
    </>
}