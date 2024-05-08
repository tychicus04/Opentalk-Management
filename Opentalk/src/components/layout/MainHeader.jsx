import React from "react";

const MainHeader = () => {
  return (
    <header className="header-banner">
      <div className="overlay"></div>
      <div className="animated-text overlay-content">
        <h1>
          Welcome to <span className="main-color">TECH TALKS</span>
        </h1>
        <h4>Explore more about Tech with us!</h4>
      </div>
    </header>
  );
};

export default MainHeader;
