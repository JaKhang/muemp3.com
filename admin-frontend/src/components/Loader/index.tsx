import React from "react";
import "./style.css";
const Loader = () => {
  return (
    <div className="loader__container">
      <div className="loader__blur"></div>
      <span className="loader"></span>
    </div>
  );
};

export default Loader;
