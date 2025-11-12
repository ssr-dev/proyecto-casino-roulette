import React, { useState, useEffect, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";

import RouletteWheel from "./components/RouletteWheel";
import BettingTable from "./components/BettingTable";
import ChipSelector from "./components/ChipSelector";
import GameInfo from "./components/GameInfo";
import HistoryPanel from "./components/HistoryPanel";
import HotColdStats from "./components/HotColdStats";

import "./RoulettePage.css";
import RouletteAPI from "../../../interceptors/axios.jsx";

const RoulettePage = ({ user, setUser }) => {
  const navigate = useNavigate();

  // ==================== ESTADOS ====================
  const [credits, setCredits] = useState(user?.balance ?? 0);
  const [selectedChip, setSelectedChip] = useState(1);
  const [currentBets, setCurrentBets] = useState([]);
  const [spinning, setSpinning] = useState(false);
  const [winningNumber, setWinningNumber] = useState(null);
  const [lastWinAmount, setLastWinAmount] = useState(0);
  const [history, setHistory] = useState([]);

  // ==================== VALIDACIÓN USUARIO ====================
  useEffect(() => {
    if (!user) navigate("/login");
  }, [user, navigate]);

  // ==================== SINCRONIZAR BALANCE ====================
  useEffect(() => {
    setUser((prev) => ({ ...prev, balance: credits }));
  }, [credits]);

  // ==================== CALCULAR TOTAL DE APUESTA ====================
  const currentBetAmount = useMemo(
    () => currentBets.reduce((sum, bet) => sum + bet.amount, 0),
    [currentBets]
  );

  // ==================== COLOCAR APUESTA ====================
  const handlePlaceBet = (type, value) => {
    if (spinning) return;

    setCurrentBets((prev) => {
      const existing = prev.find((b) => b.type === type && b.value === value);
      if (existing) {
        return prev.map((b) =>
          b.type === type && b.value === value
            ? { ...b, amount: b.amount + selectedChip }
            : b
        );
      } else {
        return [...prev, { type, value, amount: selectedChip }];
      }
    });
  };

  // ==================== EFECTO PRINCIPAL (cada 90s) ====================
// ==================== EFECTO PRINCIPAL (cada 90s) ====================
useEffect(() => {
  const interval = setInterval(async () => {
    try {
      console.log("⏰ Intentando girar automáticamente...");

      if (!spinning) {
        const signal = await RouletteAPI.getWinningNumber();
        console.log("🛰️ Señal recibida:", signal);

        // Si el backend indica que debe girar (por ejemplo: signal.spin === true)
        if (signal?.spin) {
          handleSpin(signal.winningNumber);
        } else {
          // Si no llega señal, puedes hacer que gire igual con un número local
          const randomNumber = Math.floor(Math.random() * 37);
          console.log("🎲 Giro local automático:", randomNumber);
          handleSpin(randomNumber);
        }
      }
    } catch (error) {
      console.error("❌ Error al obtener señal de ruleta:", error);
    }
  }, 90000); // 90 000 ms = 1.5 minutos

  return () => clearInterval(interval);
}, [spinning]); // <== agregamos 'spinning' para evitar condiciones viejas



  // ==================== GIRAR RULETA ====================
// ==================== GIRAR RULETA ====================
const handleSpin = async (forcedNumber = null) => {
  if (spinning) return;

  setSpinning(true);
  setWinningNumber(null);
  setLastWinAmount(0);

  try {
    // Si viene número forzado (desde backend o intervalo), úsalo
    const resultNumber =
      forcedNumber ?? (await RouletteAPI.getWinningNumber()).winningNumber;

    console.log("🎯 Girando con número:", resultNumber);
    setWinningNumber(resultNumber);

    // Deja que la animación se reproduzca y el componente RouletteWheel
    // llame a onSpinEnd cuando termine.
  } catch (error) {
    console.error("Error durante el giro:", error);
    setSpinning(false);
  }
};



  const handleClearBets = () => {
    if (!spinning) setCurrentBets([]);
  };

  // ==================== RENDER ====================
  return (
    <div className="app-container">
      <div className="main-grid">
        <div className="left-panel">
          <HistoryPanel history={history} />
          <HotColdStats history={history} />
        </div>

        <div className="center-panel">
          <RouletteWheel
            spinning={spinning}
            winningNumber={winningNumber}
            onSpinEnd={() => setSpinning(false)}
          />
        </div>

        <div className="right-panel">
          <GameInfo
            credits={credits}
            currentBetAmount={currentBetAmount}
            lastWinAmount={lastWinAmount}
            playerName={user.name}
          />
          <ChipSelector
            selectedChip={selectedChip}
            onSelectChip={setSelectedChip}
          />
        </div>
      </div>

      <BettingTable
        onPlaceBet={handlePlaceBet}
        currentBets={currentBets}
        winningNumber={winningNumber}
      />

      <AnimatePresence>
        {winningNumber !== null && !spinning && (
          <motion.div
            className="overlay"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.3 }}
          >
            <motion.div
              className="winner-modal"
              initial={{ scale: 0.8, y: -50 }}
              animate={{ scale: 1, y: 0 }}
              exit={{ scale: 0.8, y: 50 }}
              transition={{ type: "spring", stiffness: 100, damping: 10 }}
            >
              <h2 className="winner-title">¡GANADOR!</h2>
              <p className="winner-number">{winningNumber}</p>
              <p className="winner-amount">
                Has ganado:
                <span className="amount">${lastWinAmount.toLocaleString()}</span>
              </p>
              <motion.button
                className="close-button"
                onClick={() => setWinningNumber(null)}
                whileHover={{ scale: 1.05 }}
                whileTap={{ scale: 0.95 }}
              >
                Cerrar
              </motion.button>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default RoulettePage;

