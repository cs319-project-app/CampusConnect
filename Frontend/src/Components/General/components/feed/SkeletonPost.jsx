// SkeletonPost.js
import React from 'react';
import './SkeletonPost.css'; // Import the CSS file

const SkeletonPost = () => {
  return (
    <div className="skeleton-post">
      <div className="postTop">
        <div className="postTopLeft">
          <div className="skeleton-element skeleton-avatar"></div>
          <div className="skeleton-element skeleton-line" style={{ width: '60%' }}></div>
        </div>
      </div>
      <div className="postCenter">
        <div className="skeleton-element skeleton-img"></div>
      </div>
      <div className="postBottom">
        <div className="postBottomLeft">
          <div className="skeleton-element skeleton-line" style={{ width: '30%' }}></div>
        </div>
        <div className="postBottomRight">
          <div className="skeleton-element skeleton-line" style={{ width: '20%' }}></div>
        </div>
      </div>
    </div>
  );
};

export default SkeletonPost;
