import React from "react";
import casinoImg from "../assets/casino.png";
import { User, Home } from "lucide-react";
import { useNavigate } from "react-router-dom";
import "./pagesCss/Reglas.css";

const Reglas = ({ user }) => {
  const navigate = useNavigate();

  return (
    <div className="reglas-container">
      {/* Título superior izquierda */}
      <div className="login-title">
        <h1>Roulette</h1>
        <img src={casinoImg} alt="icon" className="title-img" />
      </div>

      <div className="header-right-gold">
        <span className="user-label">{user ? user.name : "NoUser"}</span>
        <User size={24} />
      </div>

      {/* Contenido central */}
      <div className="reglas-content">
        <div className="reglas-card">
          <h1>Rules of the Game</h1>

          <div className="reglas-list">
            <div className="regla-item">
              <h2>Red / Black : 1:1</h2>
              <p>Choose the color of the result. High probability • Even payout.</p>
            </div>

            <div className="regla-item">
              <h2>Even / Odd : 1:1</h2>
              <p>
                Bet on whether the number will be even or odd. Covers 18 numbers.
              </p>
            </div>

            <div className="regla-item">
              <h2>1–18 / 19–36 : 1:1</h2>
              <p>
                Bet low (1–18) or high (19–36). Low risk, even payout.
              </p>
            </div>

            <div className="regla-item">
              <h2>Dozens : 2:1</h2>
              <p>
                Select a dozen: 1st (1–12), 2nd (13–24), or 3rd (25–36). 12 numbers per bet.
              </p>
            </div>

            <div className="regla-item">
              <h2>Columns : 2:1</h2>
              <p>
                Choose one of the three columns on the table. 12 numbers per bet.
              </p>
            </div>

            <div className="regla-item">
              <h2>Full Number : 35:1</h2>
              <p>
                Bet on a single number. Maximum payout, but lower probability.
              </p>
            </div>

            <div></div>

            <div>
              <button
                className="login-home-btn"
                type="button"
                onClick={() => navigate("/")}
                aria-label="Volver al inicio"
              >
                <Home size={44} strokeWidth={2.2} color="white" />
              </button>
            </div>


          </div>
        </div>
      </div>
    </div>
  );
};

export default Reglas;
