import React, { useState } from "react";
import casinoImg from "../assets/casino.png";
import { User, Home } from "lucide-react";
import { useNavigate } from "react-router-dom";
import "./pagesCss/Login.css";

const Login = ({ setUser, user }) => {
  const navigate = useNavigate();
  const [name, setName] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
    if (name.trim() === "") {
      alert("Por favor, ingresa un nombre válido.");
      return;
    }
    setUser({ name, money: 1000 });
    navigate("/roulette");
  };

  return (
    <div className="login-container">
      <div className="login-title">
        <h1>Roulette</h1>
        <img src={casinoImg} alt="icon" className="title-img" />
      </div>

      {/* <div className="login-new-button"> */}
      <div className="login-new-button">
        <span className="user-label">{user ? user.name : "NoUser"}</span>
        <User size={24} />
      </div>

      <div className="login-card login-card-centered">
        <div className="login-header login-header-centered">
          <h2>¿WHAT'S YOUR NAME?</h2>
        </div>
        <form className="login-form login-form-centered" onSubmit={handleLogin}>
          <input
            id="nombre"
            name="nombre"
            type="text"
            required
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder=""
            className="login-input-centered"
            autoComplete="off"
          />
          <button type="submit" className="login-submit login-submit-yellow">
            ENTER
          </button>
        </form>

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
  );
};

export default Login;
