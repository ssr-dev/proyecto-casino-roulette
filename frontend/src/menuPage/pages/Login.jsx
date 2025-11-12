import React, { useState } from "react";
import RouletteIcon from "../components/RouletteIcon";
import { User, Home } from "lucide-react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import { createUser, getUserByName } from "../api";

const Login = () => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError(null);
    if (!name || name.trim().length === 0) {
      setError('Por favor ingresa un nombre');
      return;
    }
    try {
      setLoading(true);
      const user = await getUserByName(name.trim());
      // store user in localStorage for later use
      localStorage.setItem('ruletaUser', JSON.stringify(user));
      navigate('/roulette');
    } catch (err) {
      setError(err.message || 'Usuario no encontrado');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateNew = async () => {
    setError(null);
    if (!name || name.trim().length === 0) {
      const promptName = window.prompt('Ingrese el nombre para el nuevo usuario:');
      if (!promptName) return;
      setName(promptName);
    }
    try {
      setLoading(true);
      const user = await createUser((name || '').trim() || prompt('Ingrese nombre para crear usuario'), 1000);
      localStorage.setItem('ruletaUser', JSON.stringify(user));
      navigate('/roulette');
    } catch (err) {
      setError(err.message || 'Error al crear usuario');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      {/* Título superior izquierda */}
      <div className="login-title">
        <RouletteIcon />
        <h1>Rulette</h1>
      </div>

      {/* Botón Nuevo superior derecha */}
      <div className="login-new-button">
        <button onClick={handleCreateNew} type="button">
          <User size={16} />
          <span>Nuevo</span>
        </button>
      </div>

      {/* Formulario */}
      <div className="login-card login-card-centered">
        <div className="login-header login-header-centered">
          <h2>¿COMO QUIERES LLAMARTE?</h2>
        </div>
        <form className="login-form login-form-centered" onSubmit={handleLogin}>
          <input
            id="nombre"
            name="nombre"
            type="text"
            required
            placeholder="Ingresa tu nombre"
            className="login-input-centered"
            autoComplete="off"
            value={name}
            onChange={(e) => setName(e.target.value)}
            aria-label="Nombre de usuario"
          />
          <button type="submit" className="login-submit login-submit-yellow" disabled={loading}>
            {loading ? 'Ingresando...' : 'INGRESAR'}
          </button>
          {error && <div style={{color: 'salmon', marginTop: '8px'}}>{error}</div>}
        </form>
        <button
          className="login-home-btn"
          type="button"
          onClick={() => navigate("/")}
          aria-label="Volver al inicio"
        >
          <Home size={44} strokeWidth={2.2} />
        </button>
      </div>
    </div>
  );
};

export default Login;
