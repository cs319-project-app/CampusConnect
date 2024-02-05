import "./modalBRAdd.css";

import {useState} from 'react';
import { useEffect } from 'react';
export default function ModalBRAdd({modal, toggleModal, user,post, onItemShare}) {
    const [file, setFile] = useState(null);
    const [foundInLocation, setFoundInLocation] = useState('');
    const [itemName, setItemName] = useState('');
    const [description, setDescription] = useState('');
    const [location, setLocation] = useState('');
    const handleToggleModal = () => {
      if (modal) {
        setItemName('');
        setDescription('');
        setLocation('');
        setFoundInLocation('');
        setFile(null);
      }
      toggleModal(); // Call the original toggleModal passed as a prop
    };
    useEffect(() => {
      if (post && modal) {
          setItemName(post.itemName);
          setDescription(post.description);
          setLocation(post.location);
          setFoundInLocation(post.foundInLocation);
      }
  }, [post, modal]);
    const handleItemNameChange = (e) => {
        setItemName(e.target.value);
      };
      const handleDescriptionChange = (e) => {
        setDescription(e.target.value);
      };
      const handleLocationChange = (e) => {
        setLocation(e.target.value);
      };
      const handleFoundLocationChange = (e) => {
        setFoundInLocation(e.target.value);
      };

      const handleShareItemClick = () => {
        const filePath = `./assets/item/${file.name}`;
        if(itemName == '' && description == '' && location == ''){
            console.log("Enter at least one input");
        }
        else{
            // TODO: Send postText and tags to the backend (you can use an API or any other method)
        console.log('Sending to backend:', itemName, description, location);
        
        //CORRECT HERE
        // Create a new item object
        const newItem = {
            itemName: itemName,
            description: description,
            status: 'found',
            founderId: user.user_id,
            location: location,
            foundInLocation:foundInLocation,
            imagePath: filePath,
        };

        // Call the onShare function passed from Feed with the new post
        onItemShare(newItem);
        setItemName('');
        setDescription('');
        setLocation('');
        setFoundInLocation('');
        setFile(null);  
        handleToggleModal();
            }
      }

  console.log("user:"+user?.username);
  return (
    <>

      {modal && (
        <div className="modal">
          <div onClick={handleToggleModal} className="overlay"></div>
          <div className="wrapper">
            <div className="modal-content2">
              <div className="postTop">
                <div className="postTopLeft">
                    <img className="postProfileImg" src={user?.userPhotoPath} alt="" />
                    <span className="postUsername">{user?.username}</span>
                    <span className="postDate">sad</span> {/* xx/xx/xxxx at xx.xx */}
                </div>
              </div>
              {/* <p className="postDescription">
                {post?.desc}
              </p> */}
              <div className="inputWrap">
              <input className="input" 
                    placeholder="Item Name"
                    value={itemName}
                    onChange={handleItemNameChange}
              />
              <input 
              type="file" 
              className="input" 
              onChange={(e) => setFile(e.target.files[0])} // Set the file
                />

              <input className="input" 
                    placeholder="Description"
                    value={description}
                    onChange={handleDescriptionChange}
              />
              </div>
              <div className="inputWrap">
              <input className="input" 
                    placeholder="Location"
                    value={location}
                    onChange={handleLocationChange}
              />
              <input className="input" 
                    placeholder="foundInLocation"
                    value={foundInLocation}
                    onChange={handleFoundLocationChange}
              />
              <button className="button" onClick={handleShareItemClick}> Add Item! </button>
              </div>
              <div className="modinfowrap">
                {/* <img className='postImgModalv2' src={post?.imagePath} alt="" /> */}
                  <div className="pricediv">
                    {/* <p>{post.itemAge+ " years old"}</p> */}
                    {/* <p className='price-style'>{post?.itemPrice + "TL"}</p> */}
                  </div>
              </div>
                <button className="close-modal" onClick={handleToggleModal}>
                  CLOSE
                </button>
              </div>
            </div>
        </div>
      )}
    </>
  );
}