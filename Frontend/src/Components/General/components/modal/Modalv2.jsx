import "./modalv2.css";

export default function Modalv2({modal, toggleModal, user, post}) {

  const itemName = "ASDSADASDASDSADSADSADASDA"
  const item_age = "3 years old"
  const item_price= "100"
  //normalde bu üsttekiler posttan gelecek. post.item_price vs
  console.log("user:"+user?.username);
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
              <p className="postDescription">
                {post?.desc}
              </p>
              <div className='item-name'>{post?.itemName}</div>
              <div className="modinfowrap">
                <img className='postImgModalv2' src={post?.imagePath} alt="" />
                  <div className="pricediv">
                    <p>{post.itemAge+ " years old"}</p>
                    <p className='price-style'>{post?.itemPrice + "TL"}</p>
                  </div>
              </div>
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