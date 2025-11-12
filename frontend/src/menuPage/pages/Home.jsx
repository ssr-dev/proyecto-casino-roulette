import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { User } from "lucide-react";
import GameChipIcon from "../components/GameChipIcon";
import RouletteIcon from "../components/RouletteIcon";
import "./Home.css";

const Home = () => {
  const buttons = [
    { path: "/roulette", label: "JUGAR" },
    { path: "/login", label: "LOG IN" },
    { path: "/reglas", label: "REGLAS" },
  ];

  const [userName, setUserName] = useState('NoUser');

  useEffect(() => {
    try {
      const stored = localStorage.getItem('ruletaUser');
      if (stored) {
        const u = JSON.parse(stored);
        if (u && u.name) setUserName(u.name);
      }
    } catch (e) {
      // ignore
    }
  }, []);

  return (
    <div className="home-container">
      {/* Esquina superior derecha */}
      <div className="header-right-gold">
        <span className="user-label">{userName}</span>
        <User size={24} />
      </div>

      {/* Título central */}
      <div className="title-row">
        <h1 className="title">Rulette</h1>
        <span className="roulette-icon-home">
          <RouletteIcon />
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
