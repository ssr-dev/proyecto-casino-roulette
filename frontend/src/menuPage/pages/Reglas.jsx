import React from "react";
import casinoImg from '../assets/casino.png';
import { User } from "lucide-react";
import "./Reglas.css";

const Reglas = () => {
  return (
    <div className="reglas-container">
      {/* Título superior izquierda */}
      <div className="reglas-title">
        <h1>Rulette</h1>
        <img src={casinoImg} alt="icon" className="title-img" />
      </div>

      {/* Botón superior derecha */}
      <div className="reglas-new-button">
        <button>
          <User size={16} />
          <span>Nuevo</span>
        </button>
      </div>

      {/* Contenido central */}
      <div className="reglas-content">
        <div className="reglas-card">
          <h1>Reglas del Juego</h1>

          <div className="reglas-list">

            <div className="regla-item">
              <h2>Rojo / Negro : 1:1</h2>
              <p>Elige el color del resultado. Probabilidad alta • Pago par.</p>
            </div>

            <div className="regla-item">
              <h2>Par / Impar : 1:1</h2>
              <p>Apuesta a si el número será par o impar. Cobertura de 18 números.</p>
            </div>

            <div className="regla-item">
              <h2>1–18 / 19–36 : 1:1</h2>
              <p>Apuesta a bajo (1–18) o alto (19–36). Riesgo bajo, pago par.</p>
            </div>

            <div className="regla-item">
              <h2>Docenas : 2:1</h2>
              <p>Selecciona una docena: 1ª (1–12), 2ª (13–24) o 3ª (25–36). 12 números por apuesta.</p>
            </div>

            <div className="regla-item">
              <h2>Columnas : 2:1</h2>
              <p>Elige una de las tres columnas del tapete. 12 números por apuesta.</p>
            </div>

            <div className="regla-item">
              <h2>Número Pleno : 35:1</h2>
              <p>Apuesta a un solo número. Máxima ganancia, pero menor probabilidad.</p>
            </div>

            <div className="regla-item">
              <h2>División : 17:1</h2>
              <p>Apuesta entre dos números adyacentes. Se colocan las fichas en la línea que los divide.</p>
            </div>

            <div className="regla-item">
              <h2>Calle : 11:1</h2>
              <p>Apuesta a tres números consecutivos en una fila.</p>
            </div>

            <div className="regla-item">
              <h2>Cuadro : 8:1</h2>
              <p>Apuesta a cuatro números que forman un cuadrado en el tapete.</p>
            </div>

            <div className="regla-item">
              <h2>Línea Doble : 5:1</h2>
              <p>Apuesta a seis números (dos filas de tres). Moderado riesgo y cobertura amplia.</p>
            </div>

            <div className="reglas-footer">
              <p>🎯 ¡Diviértete y juega responsablemente!</p>
            </div>

          </div>
        </div>
      </div>
    </div>
  );
};

export default Reglas;
