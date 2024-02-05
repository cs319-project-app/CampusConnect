
//import { Grid } from "@mui/material"
import "../lost&found_page/lsfeed.css" //this import style is not recommended
import Masonry from "react-masonry-css";
import DOCard from "../../components/card/DOCard";
import { useEffect, useState } from "react";
import axios from "axios";
import DOCardSkeleton from "./DOCardSkeleton";
import ModalDOAdd from "../../components/modal/ModalDOAdd";

export default function DonationFeed({user,searchTerm}) {
  const [isSet, setIsSet] = useState(false);
  const [Posts, setPosts] = useState([]);
  const userId = user.user_id;
  useEffect(() => {
    setIsSet(false);
    const getPosts = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/donate/retrieveAllItems`);
        const filteredPosts = searchTerm
          ? response.data.filter(post => post.itemName.toLowerCase().includes(searchTerm.toLowerCase()))
          : response.data;
        setPosts(filteredPosts); 
        setIsSet(true);
      } catch (error) {
        console.error('Error fetching posts:', error);
      }
    };
  
    getPosts();
  }, [searchTerm]);
  

  const breakpoints = {
    default: 4,
    1100: 2,
    700: 1
  }
  
  const [modal, setModal] = useState(false);

    const toggleModal = () => {
        setModal(!modal);
    };

    if(modal) {
        document.body.classList.add('active-modal')
    } else {
        document.body.classList.remove('active-modal')
    }

    const createItem = async (postItem) => {
      try {
        console.log("Hey");
        console.log(postItem);
        console.log("Hey");
        const response = await axios.post(`http://localhost:8080/v1/lost-and-found/post`, postItem, {
          params: { userId }
          
        });
        setPosts(prevPosts => [response.data, ...prevPosts]);
        console.log(response.data);
      } catch (error) {
        console.error('Error creating post:', error);
      }
  
    };

    const handleNewItemShare = (newItem) => {
      createItem(newItem);
    };

  return (
    <div className="LSfeed">
        <div className="LSfeedWrapper">
        <div className="buttonwrap">
        <span className="page-title">Donations</span>
        <button className="plus-button" onClick={toggleModal}>+</button>
        </div>
        <ModalDOAdd modal={modal} toggleModal={toggleModal} user={user} onItemShare={handleNewItemShare}/>
        
                <Masonry
                breakpointCols={breakpoints}
                className="my-masonry-grid"
                columnClassName="my-masonry-grid_column">

{!isSet
          ? Array(10).fill().map((_, index) => <DOCardSkeleton key={index} />)
          : Posts.map(post => <DOCard key={post.id} post={post} currentUser={user}/>)
        }

                </Masonry>

                {/*
                <Grid item xs={10} md={5} lg={3}>
                <LSCard></LSCard>
                </Grid>

                <Grid item xs={3}>
                <LSCard></LSCard>
                </Grid>

                <Grid item xs={3}>
                <LSCard></LSCard>
                </Grid>

                <Grid item xs={3}>
                <LSCard></LSCard>
                </Grid>*/}

        </div>
    </div>
  )
}
