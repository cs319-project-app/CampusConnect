import React from 'react'

import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'
//import given_code_icon from '../../Assets/givencode.png'

import { useState } from 'react'
import {LoginRegisterHeader} from '../LoginSignup/LoginRegisterHeader'
import axios from 'axios';
import {  useNavigate} from 'react-router-dom';
export const ForgotPasswordPage = () => {
    const navigate = useNavigate();
    const [action,setAction] = useState("Get Your New Password");
    const [bilkentMail, setMail] = useState();
    const [code, setCode] = useState();
    const [password, setNewPassword] = useState();

    const handleSubmit = async(e) => {
        e.preventDefault();
        const infos = {bilkentMail,code,password};
        //User bilgileri buradan backende bağlanacak alt satırı silip
        try {
            const response = await axios.put('http://localhost:8080/v1/forgetPassword', infos).then(
                (response) => {
                    if (response.status === 200) {
                        console.log('Data sent successfully');
                        navigate("/");
                    } else {
                        navigate("/forgotpassword");
                        console.error('Failed to send data:', response.statusText);
                    }
                }
            )
        } catch (error) {
            setCode("");
            setNewPassword("");
            setMail("");
            navigate("/forgotpassword");
            console.error('Password Failed:', error.message);
        }
        console.log(infos);
    }

    return <>
    <div className ='inputs'>
    <div className = 'container'>
    <LoginRegisterHeader action={action} />
        <form onSubmit={handleSubmit}>

            <div className="input">
                <img src={email_icon} alt="" />
                <input type="email" placeholder='Bilkent Mail' required
                    value = {bilkentMail}
                    onChange={(e) => setMail(e.target.value)}/>
            </div>

            <div className="input">
                <img  alt="" />
                <input type="givenCode" placeholder='Enter the code given at the register' required
                    value = {code}
                    onChange={(e) => setCode(e.target.value)}/>
            </div>

            <div className="input">
                <img src={password_icon} alt="" />
                <input type="password" placeholder='New Password' required
                    value = {password}
                    onChange={(e) => setNewPassword(e.target.value)}/>
            </div>

            <div className="submit-container">
                <button className='submit'> Change Password!</button>
            </div>

        </form>
        </div>
        </div>
    </>
}
