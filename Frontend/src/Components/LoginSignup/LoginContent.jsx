import { Navigate } from 'react-router-dom';
import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'
import { useState } from 'react'
import axios from 'axios';
import {  useLocation,useNavigate} from 'react-router-dom';
import { Link } from 'react-router-dom';

export const LoginContent = () => {
    const navigate = useNavigate();
    const [usernameOrEmail, setMail] = useState();
    const [password, setPW] = useState();

    const handleSubmit = async(e) => {
        e.preventDefault();
        const infos = {usernameOrEmail,password};
        try {
            const response = await axios.post('http://localhost:8080/v1/user/login', infos);
            if (response.status === 200) {
                const user = response.data;
                console.log('Login successful:', user.user_id);
                navigate("/dashboard", {state: {user: user}});
            }
        } catch (error) {
            console.error('Login failed:', error.response?.data || error.message);
        }
    }

    return <>
    <form onSubmit={handleSubmit}>
        <div className="input">
            <img src={email_icon} alt="" />
            <input type="text" placeholder='Bilkent Mail or Username' required
                value = {usernameOrEmail}
                onChange={(e) => setMail(e.target.value)}/>
        </div>
        <div className="input">
            <img src={password_icon} alt="" />
            <input type="password" placeholder='Password' required
                value = {password}
                onChange={(e) => setPW(e.target.value)}/>
        </div>
        <div className="forgot-password">Forgot your password? <Link to="/forgotpassword">Click Here!</Link></div>
            <div className="submit-container">
                <button className='submit'>
                    Login
                </button>
            </div>
        </form>
    </>
}