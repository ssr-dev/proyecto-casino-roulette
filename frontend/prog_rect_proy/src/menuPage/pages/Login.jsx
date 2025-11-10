import React, { useState } from "react";
import casinoImg from "../assets/casino.png";
import { User, Home } from "lucide-react";
import { useNavigate } from "react-router-dom";
import "./pagesCss/Login.css";
import RouletteAPI from "../../interceptors/axios.jsx"; 

const Login = ({ setUser, user }) => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [money, setMoney] = useState(1000);
  const [loading, setLoading] = useState(false); 

  const handleLogin = async (event) => {

    event.preventDefault();

    if (name.trim() === "") {
      alert("Por favor, ingresa un nombre válido.");
      return;
    }

    setLoading(true);

    try {
      //  Envía los datos del usuario al backend
      const newUser = await RouletteAPI.createUser({ name, money:1000 });

      // Guarda el usuario en el estado global y localStorage
      setUser(newUser);
      localStorage.setItem("user", JSON.stringify(newUser));

      // Redirige a la ruleta
      navigate("/roulette");
    } catch (error) {
      console.error("X Error al crear usuario:", error);
      alert("Error al conectar con el servidor. Intenta nuevamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-title">
        <h1>Roulette</h1>
        <img src={casinoImg} alt="icon" className="title-img" />
      </div>

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
            placeholder="PUT YOUR NAME HERE"
            className="login-input-centered"
            autoComplete="off"
          />

          <button
            type="submit"
            className="login-submit login-submit-yellow"
            disabled={loading}
          >
            {loading ? "Connecting..." : "ENTER"}
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
