import React from 'react'
import "../lost&found_page/lsfeed.css"
import "./welcomefeed.css"

export default function WelcomeFeed() {
  return (
    <div className="LSfeed">
        <div className="LSfeedWrapper">
            <div className='texttype'>
              Welcome to the CampusConnect!
              From the bar on the left side start your surfing and have fun!
              
            </div>
            <div className="texttype2">
            <img className="sample-image" src="assets/campusconnect.jpeg" alt="" />   
            </div>
        </div>
    </div>
  )
}
