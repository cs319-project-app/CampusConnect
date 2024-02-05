import "./modaldonation.css";

export default function Modal_donation({modal, toggleModal, user, post}) {


  //normalde bu üsttekiler posttan gelecek. post.item_price vs

  return (
    <>

      {modal && (
        <div className="modal">
          <div onClick={toggleModal} className="overlay"></div>
          <div className="wrapper">
            <div className="modal-content2">
              <div className="postTop">
                <div className="postTopLeft">
                    <img className="postProfileImg" src={user?.userPhotoPath} alt="" />
                    <span className="postUsername">{user?.username}</span>
                    <span className="postDate">{post?.creationTimestamp}</span> {/* xx/xx/xxxx at xx.xx */}
                </div>
              </div>
              <div className='item-name'>{post?.itemName}</div>
              <p className="postDescription">
                {post?.description}
              </p>
              <p className="postFoundInLocation">
                {"Item found in "+post?.foundInLocation}
              </p>
              
                <img className='postImgModalv2_do' src={post?.imagePath} alt="" />
                <button className="close-modal" onClick={toggleModal}>
                  CLOSE
                </button>
              </div>
            </div>
        </div>
      )}
    </>
  );
}