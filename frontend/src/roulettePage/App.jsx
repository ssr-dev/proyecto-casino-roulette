import React, { useState, useMemo } from "react";
import { motion, AnimatePresence } from "framer-motion";
import RouletteWheel from "./components/RouletteWheel";
import BettingTable from "./components/BettingTable";
import ChipSelector from "./components/ChipSelector";
import GameInfo from "./components/GameInfo";
import HistoryPanel from "./components/HistoryPanel";
import HotColdStats from "./components/HotColdStats";
import GameControls from "./components/GameControls";
import { getWinningNumber, calculateWinnings } from "../utils/rouletteLogic";
import "./App.css";


const INITIAL_CREDITS = 5000;

function App() {
  const [credits, setCredits] = useState(INITIAL_CREDITS);
  const [selectedChip, setSelectedChip] = useState(1);
  const [currentBets, setCurrentBets] = useState([]);
  const [spinning, setSpinning] = useState(false);
  const [winningNumber, setWinningNumber] = useState(null);
  const [lastWinAmount, setLastWinAmount] = useState(0);
  const [history, setHistory] = useState([]);

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

  const handleSpin = () => {
    if (spinning || currentBetAmount === 0 || credits < currentBetAmount) {
      return;
    }

    setSpinning(true);
    setWinningNumber(null);
    setLastWinAmount(0);

    const result = getWinningNumber();
    setWinningNumber(result);
  };

  const handleSpinEnd = () => {
    setSpinning(false);
    if (winningNumber) {
      const { winnings, newCredits } = calculateWinnings(
        currentBets,
        winningNumber,
        credits
      );
      setCredits(newCredits);
      setLastWinAmount(winnings);
      setHistory((prev) => [winningNumber.number, ...prev]);
    }
    setCurrentBets([]);
  };

  const handleClearBets = () => {
    if (spinning) return;
    setCurrentBets([]);
  };

  return (
    <div>
      <div className="app-container">
        {/* <h1 className="app-title">La Ruleta de la Fortuna</h1> */}

        <div className="main-grid">
          <div className="left-panel">

            <HistoryPanel history={history} />
            <HotColdStats history={history} />

            <GameControls
              onSpin={handleSpin}
              onClearBets={handleClearBets}
              canSpin={
                !spinning && currentBetAmount > 0 && credits >= currentBetAmount
              }
            />


          </div>

          <div className="center-panel">
            <RouletteWheel
              spinning={spinning}
              winningNumber={winningNumber}
              onSpinEnd={handleSpinEnd}
            />
          </div>

          <div className="right-panel">
            <GameInfo
              credits={credits}
              currentBetAmount={currentBetAmount}
              lastWinAmount={lastWinAmount}
            />
            <ChipSelector
              selectedChip={selectedChip}
              onSelectChip={setSelectedChip}
            />
          </div>
        </div>

        <div>
          <BettingTable
            onPlaceBet={handlePlaceBet}
            currentBets={currentBets}
            winningNumber={winningNumber}
          />
        </div>

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
                className={`winner-modal ${winningNumber.color}`}
                initial={{ scale: 0.8, y: -50 }}
                animate={{ scale: 1, y: 0 }}
                exit={{ scale: 0.8, y: 50 }}
                transition={{ type: "spring", stiffness: 100, damping: 10 }}
              >
                <h2 className="winner-title">¡GANADOR!</h2>
                <p className="winner-number">{winningNumber.number}</p>
                <p className="winner-amount">
                  Has ganado:{" "}
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
    </div>
  );
}

export default App;
