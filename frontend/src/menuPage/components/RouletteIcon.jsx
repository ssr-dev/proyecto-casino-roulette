import React from "react";
import "../components/componentsCss/RouletteIcon.css";


const RouletteIcon = () => (
  <svg
    className="roulette-icon"
    width="32"
    height="32"
    viewBox="0 0 24 24"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <circle cx="12" cy="12" r="10" className="roulette-circle" />
    <path d="M12 2V22M2 12H22" className="roulette-lines" />
    <circle cx="12" cy="12" r="1" className="roulette-center" />
  </svg>
);

export default RouletteIcon;
