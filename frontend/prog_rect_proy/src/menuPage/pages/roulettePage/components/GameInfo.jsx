import React from "react";
import { motion } from "framer-motion";
import "../cssClases/GameInfo.css";

const GameInfo = ({
  credits,
  currentBetAmount,
  lastWinAmount,
  playerName,
}) => {
  return (
    <motion.div
      className="game-info-container"
      initial={{ opacity: 0, x: -20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.5, delay: 0.2 }}
    >
      <div className="game-info-header">
        <h2 className="game-info-player">JUGADOR: {playerName}</h2>
      </div>

      <div className="game-info-row">
        <span className="label">CRÉDITO:</span>
        <span className="value credits">${credits.toLocaleString()}</span>
      </div>

      <div className="game-info-row">
        <span className="label">APUESTA ACTUAL:</span>
        <span className="value bet">${currentBetAmount.toLocaleString()}</span>
      </div>

      <div className="game-info-row">
        <span className="label">ÚLTIMA GANANCIA:</span>
        <span className="value win">${lastWinAmount.toLocaleString()}</span>
      </div>
    </motion.div>
  );
};

export default GameInfo;

