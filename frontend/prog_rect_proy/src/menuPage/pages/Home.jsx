import React from "react";
import { Link } from "react-router-dom";
import { User } from "lucide-react";
import casinoImg from "../assets/casino.png";
import GameChipIcon from "../components/GameChipIcon";
import "./pagesCss/Home.css";

const Home = ({ user }) => {
  const buttons = [
    { path: "/roulette", label: "PLAY" },
    { path: "/login", label: "LOGIN" },
    { path: "/reglas", label: "RULES" },
  ];

  return (
    <div className="home-container">
      
      {/* Esquina superior derecha */}
      <div className="header-right-gold">
        <span className="user-label">{user ? user.name : "NoUser"}</span>
        <User size={24} />
      </div>

      {/* Título central */}
      <div className="title-row">
        <h1 className="title">Roulette</h1>
        <span className="roulette-icon-home">
          {/* <RouletteIcon /> */}
          <img src={casinoImg} alt="icon" className="title-img" />
        </span>
      </div>

      {/* Panel central */}
      <div className="center-container">
        <div className="panel-glass">
          {buttons.map(({ path, label }) => (
            <Link key={label} to={path} className="menu-link">
              <button className="menu-btn-big">
                <GameChipIcon />
                <span>{label}</span>
              </button>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
