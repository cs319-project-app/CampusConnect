import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Share from "../share/Share";
import Post from "../post/Post";
import "./feed.css";
import SkeletonPost from './SkeletonPost';
export default function Feed({user, searchTerm}) {
  const userId = user.user_id;
  const [isLoading, setIsLoading] = useState(false);
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const size = 45; 
  const[pageable,setPageable] = useState(true);
  const [triggerFetch, setTriggerFetch] = useState(false);
  const [shouldFetchMore, setShouldFetchMore] = useState(true);
  const refreshPosts = () => {
    setTriggerFetch(prev => !prev); 
};

function debounce(func, wait) {
  let timeout;
  return function(...args) {
    const context = this;
    clearTimeout(timeout);
    timeout = setTimeout(() => func.apply(context, args), wait);
  };
}
const getPostsMore = async (page) => {
  try {
    const response = await axios.get(`http://localhost:8080/v1/posts/filter/${userId}`, {
      params: { page, size }
    });
    console.log("last2"+response.data.last);
    setPageable(!response.data.last);
    console.log("second"+response.data.content);
    console.log("second"+response.data.content.length);
    if (page>0) {
      setPosts(prevPosts => [...prevPosts, ...response.data.content]);
    }if(page==0){
      setPosts(response.data.content);
    }
  } catch (error) {
    console.error('Error fetching posts:', error);
    return [];
  }
};

const loadMorePosts = async () => {
  console.log("shouldFetchMore: "+shouldFetchMore);
  console.log("pageable: in load more "+pageable);
  if(shouldFetchMore){
    setPageable(true);
  }
  if (pageable && shouldFetchMore) {
    setIsLoading(true);
    await getPostsMore(page);
    setPage(prevPage => prevPage + 1);
    setIsLoading(false);
    setShouldFetchMore(false);
  }
};

  const loadMorePosts2 = async () => {
    setIsLoading(true);
    await getPostsMore(0);
    setIsLoading(false);
  };

  const handleScroll = () => {
    if (window.innerHeight + document.documentElement.scrollTop < document.documentElement.offsetHeight - 100) return;
    if (pageable) {
    console.log("User reached bottom, setting shouldFetchMore to true"); // Debug log
    setShouldFetchMore(true);
    }
  };
  

  const debouncedHandleScroll = debounce(handleScroll, 500);
  useEffect(() => {
    window.addEventListener('scroll', debouncedHandleScroll);
    return () => window.removeEventListener('scroll', debouncedHandleScroll);
  }, [debouncedHandleScroll]); 
  
  
  const filteredPosts = posts.filter(post => 
    post.content.toLowerCase().includes(searchTerm.toLowerCase())
  );
  useEffect(() => {
    loadMorePosts();
    console.log("last page "+pageable);
  }, [shouldFetchMore, searchTerm]);
  
  useEffect(() => {
    loadMorePosts2();
   }, [triggerFetch]);
  const createPost = async (postData) => {
    setPageable(false);
    try {
      console.log(postData);
      const response = await axios.post(`http://localhost:8080/v1/posts/create/`, postData, {
        params: { userId }
      });
      if (response.data) {
        console.log("atıyorum");
        setPage(0);
        setShouldFetchMore(true);
        refreshPosts();
      }
    } catch (error) {
      console.error('Error creating post:', error);
    }
  };

  const handleNewPostShare = (newPost) => {
    setPageable(true);
    createPost(newPost);
  };

  return (
    <div className="feed">
      <div className="feedWrapper">
        <Share posts={posts} onShare={handleNewPostShare}/>
        {isLoading ? (
          Array.from(new Array(5)).map((_, index) => <SkeletonPost key={index} />)
        ) : (
          filteredPosts.map(p => (
            <Post key={p.id} post={p} userLoggedIn={user} refreshPosts={refreshPosts}/>
          ))
        )}
      </div>
    </div>
  );
}
