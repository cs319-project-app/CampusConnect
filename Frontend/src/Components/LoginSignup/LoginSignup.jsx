import React, { useState } from 'react'
import './LoginSignup.css'
import { LoginRegisterHeader } from './LoginRegisterHeader'
import { LoginRegisterButtons } from './LoginRegisterButtons'
import { LoginRegisterContent } from './LoginRegisterContent'

const LoginSignup = () => {

    const [action,setAction] = useState("Login");

  return (
    <div className = 'container'>
    
        <LoginRegisterHeader action={action} />
        <LoginRegisterButtons action={action} setAction={setAction} />
        <LoginRegisterContent action={action} />
      
    </div>
  )
}

export default LoginSignup
