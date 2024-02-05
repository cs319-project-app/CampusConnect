import "./modaldonation.css";

export default function Modal_borrow({modal, toggleModal, user, post}) {

  const itemName = "ASDSADASDASDSADSADSADASDA"

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
                    <img className="postProfileImg" src={post.user.userPhotoPath} alt="" />
                    <span className="postUsername">{post.user.username}</span>
                    <span className="postDate">{post.creationTimestamp}</span> {/* xx/xx/xxxx at xx.xx */}
                </div>
              </div>
              <p className="postDescription">
                {post?.description}
              </p>
              <div className='item-name'>{itemName}</div>
                <img className='postImgModalv2_do' src={post.imagePath} alt="" />
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