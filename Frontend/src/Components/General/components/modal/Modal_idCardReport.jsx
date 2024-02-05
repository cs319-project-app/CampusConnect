import "./modal_idCardReport.css";

import {useState} from 'react';

export default function Modal_idCardReport({modal, toggleModal, user, onIdReport}) {
    
    const [losedIdNo, setLosedIdNo] = useState('');
    const [idLoserName, setIdLoserName] = useState('');
    const [idLoserDepartment, setIdLoserDepartment] = useState('');

    const handleToggleModal = () => {
      if (modal) {
        setLosedIdNo('');
        setIdLoserName('');
        setIdLoserDepartment('');
      }
      toggleModal(); // Call the original toggleModal passed as a prop
    };
    const handleLosedIdNoChange = (e) => {
        setLosedIdNo(e.target.value);
      };
      const handleIdLoserNameChange = (e) => {
        setIdLoserName(e.target.value);
      };
      const handleIdLoserDepartmentChange = (e) => {
        setIdLoserDepartment(e.target.value);
      };

      const handleShareIdReportClick = () => {
        if(losedIdNo == '' && idLoserName == '' && idLoserDepartment == ''){
            console.log("Enter at least one input");
        }
        else{
            // TODO: Send postText and tags to the backend (you can use an API or any other method)
        console.log('Sending to backend:', losedIdNo, idLoserName, idLoserDepartment);

        // Create a new item object
        const newIdReport = {
            losedIdNo: losedIdNo,
            idLoserName: idLoserName,
            idLoserDepartment: idLoserDepartment,
            user: user,
        };

        // Call the onShare function passed from Feed with the new post
        onIdReport(newIdReport);
        setLosedIdNo('');
        setIdLoserName('');
        setIdLoserDepartment('');
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
                    placeholder="Losed ID No"
                    value={losedIdNo}
                    onChange={handleLosedIdNoChange}
              />

              <input className="input" 
                    placeholder="ID Loser Name"
                    value={idLoserName}
                    onChange={handleIdLoserNameChange}
              />
              </div>
              <div className="inputWrap">
              <input className="input" 
                    placeholder="ID Loser Department"
                    value={idLoserDepartment}
                    onChange={handleIdLoserDepartmentChange}
              />
              <button className="button" onClick={handleShareIdReportClick}> Report ID! </button>
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