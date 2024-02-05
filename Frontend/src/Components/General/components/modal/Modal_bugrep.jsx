import ContactSupportIcon from '@mui/icons-material/ContactSupport';
import "./modalbugrep.css"

export default function Modal_bugrep({modal, toggleModal, user}) {

  const itemName = "Contact Devs"

  //normalde bu üsttekiler posttan gelecek. post.item_price vs

  return (
    <>

      {modal && (
        <div className="modal">
          <div onClick={toggleModal} className="overlay"></div>
          <div className="wrapper">
                <div className="modal-content2">
                
                <div className='item-name'>{itemName}</div>
                    <button className="close-modal" onClick={toggleModal}>
                    CLOSE
                    </button>
                        
                    <div className="postDescription22">
                    <ContactSupportIcon/>
                        Please contact us with the bug description, you can do it by telling
                        how did you end up with this bug. Additionally, sending the screenshot
                        of the bug would be helpful. We will come back to you in maximum 2-3 business
                        days.
                    </div>
                    <div className="postDescription2">
                        Thanks for choosing CampusConnect!
                    </div>
                    <a className="postDescription3" href="mailto:fullstackfidelity@gmail.com" target="_blank" rel="noopener noreferrer">
                        fullstackfidelity@gmail.com
                    </a>
                </div>
            </div>
        </div>
      )}
    </>
  );
}