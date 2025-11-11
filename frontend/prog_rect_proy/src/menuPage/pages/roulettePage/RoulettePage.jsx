import React, { useState, useMemo, useEffect } from "react";
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

  // 🔹 Verifica usuario
  useEffect(() => {
    if (!user) {
      navigate("/login");
    }
  }, [user, navigate]);

  if (!user) return null;

  const [credits, setCredits] = useState(user.money);
  const [selectedChip, setSelectedChip] = useState(1);
  const [currentBets, setCurrentBets] = useState([]);
  const [spinning, setSpinning] = useState(false);
  const [winningNumber, setWinningNumber] = useState(null);
  const [lastWinAmount, setLastWinAmount] = useState(0);
  const [history, setHistory] = useState([]);
  const [spinSignal, setSpinSignal] = useState(false); // ✅ ahora sí existe

  // Mantener créditos sincronizados con el usuario global
  useEffect(() => {
    setUser({ ...user, money: credits });
  }, [credits, setUser, user]);

  const currentBetAmount = useMemo(() => {
    return currentBets.reduce((sum, bet) => sum + bet.amount, 0);
  }, [currentBets]);

  const handlePlaceBet = (type, value) => {
    if (spinning) return;

    const betIndex = currentBets.findIndex(
      (bet) => bet.type === type && bet.value === value
    );
    const newBets = [...currentBets];

    if (betIndex > -1) {
      newBets[betIndex].amount += selectedChip;
    } else {
      newBets.push({ type, value, amount: selectedChip });
    }

    setCurrentBets(newBets);
  };

  // 🔹 Verifica cada 5s si el backend manda señal para girar
  useEffect(() => {
    const interval = setInterval(async () => {
      try {
        const signal = await RouletteAPI.getWinningNumber();
        if (signal.spin && !spinning) {
          setSpinSignal(true);
        }
      } catch (err) {
        console.error("Error al verificar spin signal:", err);
      }
    }, 5000);

    return () => clearInterval(interval);
  }, [spinning]);

  // 🔹 Cuando llega la señal, girar la ruleta
  useEffect(() => {
    if (spinSignal && !spinning) {
      handleSpin();
    }
  }, [spinSignal]);

  const handleSpin = async () => {
    if (spinning || currentBetAmount === 0 || credits < currentBetAmount) return;

    setSpinning(true);
    setWinningNumber(null);
    setLastWinAmount(0);

    try {
      // Enviar apuestas al backend
      await RouletteAPI.postBets(user.id || user.name, currentBets);

      // Obtener número ganador
      const result = await RouletteAPI.getWinningNumber();
      setWinningNumber(result.number);

      // Simula cálculo de ganancias (debes tener endpoint real)
      const winnings = Math.floor(Math.random() * currentBetAmount * 2);
      const newCredits = credits - currentBetAmount + winnings;

      setCredits(newCredits);
      setLastWinAmount(winnings);
      setHistory((prev) => [result.number, ...prev]);
    } catch (error) {
      console.error("Error durante el giro:", error);
    } finally {
      setCurrentBets([]);
      setSpinning(false);
      setSpinSignal(false);
    }
  };

  const handleClearBets = () => {
    if (!spinning) setCurrentBets([]);
  };

  // ----------------------------------------------------------------------------------------------

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
        {winningNumber && !spinning && (
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
                <span className="amount">
                  ${lastWinAmount.toLocaleString()}
                </span>
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

