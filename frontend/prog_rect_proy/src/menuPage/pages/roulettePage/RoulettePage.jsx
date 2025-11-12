import React, { useState, useEffect, useMemo, useRef } from "react";

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
  const betsRef = useRef(currentBets);

  // ==================== VALIDACIÓN USUARIO ====================
  useEffect(() => {
    if (!user) navigate("/login");
  }, [user, navigate]);

  useEffect(() => {
    betsRef.current = currentBets;
  }, [currentBets]);

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
        if (!spinning) {
          const signal = await RouletteAPI.getWinningNumber();
          console.log("🛰️ Señal recibida:", signal);

          if (signal?.spin) {
            // usa la ref aquí, no el estado cerrado
            await handleSpin(signal.winningNumber, betsRef.current);
          }
        }
      } catch (error) {
        console.error("❌ Error al obtener señal de ruleta:", error);
      }
    }, 30000);

    return () => clearInterval(interval);
  }, [spinning]);

  // ==================== GIRAR RULETA ====================
  // ==================== GIRAR RULETA ====================

  const handleSpin = async (winningNumber, currentBetsSnapshot) => {
    setSpinning(true);
    console.log("🎯 Iniciando spin con número:", winningNumber);

    const bets = currentBetsSnapshot ?? currentBets; // fallback por seguridad

    if (!bets || bets.length === 0) {
      console.warn("⚠️ No hay apuestas para enviar.");
      setSpinning(false);
      return;
    }

    try {
      setWinningNumber(winningNumber);
      console.log("📤 Enviando apuestas al backend:", bets);

      const betsToSend = bets.map((bet) => ({
        userId: user.id,
        type: bet.type,
        value: bet.value,
        amount: bet.amount,
      }));

      console.log("🧾 Formato final de apuestas (POST):", betsToSend);

      const result = await RouletteAPI.postBets(user.id, betsToSend);

      console.log("💰 Resultado de la partida:", result);

      const { totalWinning, totalBetAmount } = result;
      const netChange = totalWinning - totalBetAmount;
      setCredits((prev) => prev + netChange);

      handleClearBets();
      setSpinning(false);

      console.log(`✅ Apuestas procesadas. Ganancia neta: ${netChange}`);
    } catch (error) {
      if (error.response?.data?.message) {
        alert(error.response.data.message); // Muestra "Saldo insuficiente"
      } else {
        console.error("Error desconocido", error);
      }
      setSpinning(false);
    }
  };

  const handleClearBets = () => {
    setCurrentBets([]);
    console.log("🧹 Apuestas limpiadas después del spin.");
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
