import { LoginContent } from './LoginContent'
import { RegisterContent } from './RegisterContent'
export const LoginRegisterContent  =({action})=> {

    return (
        <div className="inputs">
            {action === "Login" ? <LoginContent />: <RegisterContent />}
    
    </div>
    )
}