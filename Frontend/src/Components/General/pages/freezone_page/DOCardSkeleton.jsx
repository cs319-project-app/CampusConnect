import React from "react";
import "./doCardSkeleton.css"; // Create and import a CSS file for styling

const DOCardSkeleton = () => {
  return (
    <div className="do-card-skeleton">
      <div className="skeleton-image"></div>
      <div className="skeleton-text"></div>
      <div className="skeleton-text short"></div>
    </div>
  );
};

export default DOCardSkeleton;
