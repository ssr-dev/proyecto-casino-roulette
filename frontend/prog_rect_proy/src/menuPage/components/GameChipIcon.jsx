import React from "react";
import "../components/componentsCss/GameChipIcon.css";


const GameChipIcon = () => (
  <svg
    className="chip-icon"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    xmlns="http://www.w3.org/2000/svg"
  >
    <circle cx="12" cy="12" r="10" className="chip-outer" />
    <circle cx="12" cy="12" r="6" className="chip-middle" />
    <circle cx="12" cy="12" r="3" className="chip-center" />
  </svg>
);

export default GameChipIcon;
