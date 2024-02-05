import React from 'react'
import { useEffect, useState } from 'react'

export default function BackToTopButton() {

    const [backToTopButton, setBackToTopButton] = useState(false);

    useEffect(() => {
        window.addEventListener("scroll", () => {
            if(window.scrollY > 100){
                setBackToTopButton(true)
            }
            else{
                setBackToTopButton(false)
            }
        })
    }, [])

    const scrollUp = () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        })
    }

  return (
    <div>
        {backToTopButton && ( <button style={{
            position: "fixed",
            bottom: "50px",
            right: "50px",
            height: "50px",
            width: "50px",
            fontSize: "25px",
            zIndex: "1009",
            borderRadius: "50%",
            color: "black",
            backgroundColor: "#C0C0C0"
        }} onClick={scrollUp}>^</button>)}
    </div>
  )
}