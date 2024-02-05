import "./rightbar.css"
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const NewsComponent = () => {
  const [articles, setArticles] = useState([]);
  const API_KEY = 'f0b3038cafcc46b9922e1933ec280068'; // Replace with your API key

  useEffect(() => {
    const fetchNews = async () => {
      try {
        const response = await axios.get('https://newsapi.org/v2/everything', {
          params: {
            q: 'computer science', // Query for computer science news
            apiKey: API_KEY,
            pageSize: 3, // Limit to 10 articles
          },
        });
        setArticles(response.data.articles);
      } catch (error) {
        console.error('Error fetching news:', error);
      }
    };

    fetchNews();
  }, []); // Empty dependency array means this effect runs once on mount

  return (
    <div className="rightbar">
    <div className="newsContainer">
  <h1>Computer Science News</h1>
  {articles.map((article, index) => (
    <div key={index} className="newsArticle">
      {article.urlToImage && (
        <img src={article.urlToImage} alt={article.title} className="newsImage" />
      )}
      <h2 className="newsTitle">{article.title}</h2>
      <p className="newsDescription">{article.description}</p>
      <a href={article.url} target="_blank" rel="noopener noreferrer" className="newsLink">Read more</a>
    </div>
  ))}
</div>
</div>
  );
};

export default NewsComponent;

  /* return (
    <div className="rightbar">
      <div className="rightbarWrapper">
        <div className="rightbarNewsContainer">
          <img className="rightBarNews" src="assets/cyber.png" alt=""  />
          <span className="newsText">
            <b>News:</b> balbablablalbalba
          </span>
          <hr className="sidebarHr"/>
          <img className="rightBarNews" src="assets/cyber.png" alt=""  />
          <span className="newsText">
            <b>News:</b> balbablablalbalba
          </span>
        </div>
      </div>
    </div>
  ) */

