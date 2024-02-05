import './share.css';
import React, { useState, useRef, useEffect } from 'react';
import { PermMedia, Label, EmojiEmotions } from "@mui/icons-material";
import Picker from '@emoji-mart/react';
import data from '@emoji-mart/data';

import {useLocation, useNavigate} from 'react-router-dom';


export default function Share({posts, onShare}) {
  const [selectedFile, setSelectedFile] = useState(null);

  const [postText, setPostText] = useState('');
  const [tags, setTags] = useState([]);
  const fileInputRef = useRef(null);
  const [isPickerVisible, setPickerVisible] = useState(false);
  const [currentEmoji, setCurrentEmoji] = useState(null);
  // Add a new state to control the visibility of the tag input
const [isTagInputVisible, setTagInputVisible] = useState(false);
const handleFileChange = async (event) => {
  const file = event.target.files[0];
  if (file) {
      setSelectedFile(file);  // Set the file to the state
      const formData = new FormData();
      formData.append('file', file);
  }
};
const location = useLocation();
  const user = location.state?.user;
  const navigate = useNavigate();



  const handleInputChange = (e) => {
    setPostText(e.target.value);
  };

  const handleShareClick = () => {
     if (fileInputRef.current.files[0] === undefined && postText == "" && tags.length==0 && currentEmoji == null) {
    //   console.log("Undefined file, warn user");
     }
    else{
      // TODO: Send postText and tags to the backend (you can use an API or any other method)
      console.log('Sending to backend:', postText, tags, fileInputRef.current.files[0], currentEmoji);

      // Create a new post object
      const newPost = {

        postId: posts.length + 1, // Assuming unique IDs for posts
        content: postText + " " + tags + (currentEmoji === null ? '' : " Feels Like: " + currentEmoji),
        imagePath: selectedFile ? './assets/post/' + selectedFile.name : '',
        user: user, // Assuming a user ID
        totalLikes: 0,
        userIdValue: 1,
        comments: [],

      };

      // Call the onShare function passed from Feed with the new post
      onShare(newPost);

    }
    
    // Optional: Clear the input field and reset tags after sharing
    setPostText('');
    setTags([]);
    setCurrentEmoji(null);
    // Create a new file input element
    const newFileInput = document.createElement("input");
    newFileInput.type = "file";
    newFileInput.accept = "image/*";
    newFileInput.style.display = "none";

    // Replace the old file input with the new one
    fileInputRef.current.parentNode.replaceChild(newFileInput, fileInputRef.current);

    // Update the ref to point to the new file input
    fileInputRef.current = newFileInput;

    // Optional: Add event listeners to the new file input if needed
    newFileInput.addEventListener("change", handleFileChange);
  };

  const handleButtonClick = () => {
    // Trigger the file input
    fileInputRef.current.click();
  };

  

  const handleTagInputChange = (e) => {
    // You can implement validation or other logic here
    // For simplicity, let's split the input value by commas to get individual tags
    const newTags = e.target.value.split(',').map(tag => tag.trim());
    //setTags(newTags);
    const sanitizedTags = newTags.map(tag => {
      // Add '#' at the beginning if it's not there
      if (!tag.startsWith('#')) {
        return `#${tag}`;
      }
      
      // Ensure there's only one '#' at the beginning
      return tag.replace(/^#+/, '#');
    });
    setTags(sanitizedTags);
  };
  const handleTagOptionClick = () => {
    setPickerVisible(false);
    // Toggle the visibility of the tag input
    setTagInputVisible(!isTagInputVisible);
  };

  const tagInputRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      // Check if the clicked element is the tag span or its child
      const isTagSpanClicked = event.target.closest('.shareOptionText') !== null;
    
      if (tagInputRef.current && !tagInputRef.current.contains(event.target) && !isTagSpanClicked) {
        // Clicked outside the tag input (excluding the tag span and its children), close it
        setTagInputVisible(false);
      }
    };
    

    // Add event listener when the component mounts
    document.addEventListener('mousedown', handleClickOutside);

    // Clean up the event listener when the component unmounts
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [tagInputRef]);

   // Add a new ref for the emoji picker
   const emojiPickerRef = useRef(null);
   const emojiPickerContainerRef = useRef(null);

   useEffect(() => {
    const handleClickOutside = (event) => {

      const isEmojiSpanClicked = event.target.closest('.shareOptionText') !== null;

      // Check if the clicked element is the emoji picker or its child
      const isEmojiPickerClicked = emojiPickerRef.current && emojiPickerRef.current.contains(event.target);
      // Check if the clicked element is within the emoji picker container
      const isEmojiPickerContainerClicked = emojiPickerContainerRef.current && emojiPickerContainerRef.current.contains(event.target);

      if (!isEmojiPickerClicked && !isEmojiSpanClicked && !isEmojiPickerContainerClicked) {
        // Clicked outside the tag input, emoji picker, and their children, close them
        setPickerVisible(false);
      }
    };

    // Add event listener when the component mounts
    document.addEventListener('mousedown', handleClickOutside);

    // Clean up the event listener when the component unmounts
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [emojiPickerRef]);

  return (
    <div className="share">
      <div className="shareWrapper">
        <div className="shareTop">

          <img className="shareProfileImg" src={user.userPhotoPath} alt="" onClick={() => navigate("/profile", {state: {user: user, clickedUser: user}})} />

          <input
            placeholder="What's in your mind?"
            className="shareInput"
            value={postText}
            onChange={handleInputChange}
          />
        </div>
        <hr className="shareHr" />
        <div className="shareBottom">
          <div className="shareOptions">
            <div className="shareOption">
              <PermMedia className="shareIcon" />
              <span className="shareOptionText" onClick={handleButtonClick}>Photo/Video</span>
              <input
                type="file"
                accept="image/*" // Allow only image files
                style={{ display: 'none' }} // Hide the input visually
                ref={fileInputRef}
                onChange={handleFileChange}
              />
            </div>
            <div className="shareOption">
              <Label htmlColor="gold" className="shareIcon" />
              <span className="shareOptionText" onClick={handleTagOptionClick}>#Tag</span>
              {isTagInputVisible && (<input
               ref={tagInputRef}
              style={{ position: 'absolute', zIndex: 2, marginTop:'50px', marginLeft:'-30px' , width:'180px'}}  
                type="text"
                placeholder="Enter tags (comma-separated)"
                className="tagInput"
                value={tags.join(', ')}
                onChange={handleTagInputChange}
              />)}
            </div>
            <div className="shareOption">
              <EmojiEmotions htmlColor="tomato" className="shareIcon" />
              <span className="shareOptionText" onClick={() => { setPickerVisible(!isPickerVisible); setTagInputVisible(false);}}>Feeling {currentEmoji}</span>
            </div>
            <div style={{ position: 'fixed', zIndex: 1, marginLeft: '100px', marginTop: '30px' }} ref={emojiPickerContainerRef}>
              {isPickerVisible && (
                <Picker
                  innerRef={emojiPickerRef}
                  data={data}
                  previewPosition='none'
                  onEmojiSelect={(e) => {
                    setCurrentEmoji(e.native);
                    setPickerVisible(!isPickerVisible);
                  }}
                />
              )}
            </div>
          </div>

          <button className="shareButton" onClick={handleShareClick}>
            Share!
          </button>
        </div>
      </div>
    </div>
  );
}
