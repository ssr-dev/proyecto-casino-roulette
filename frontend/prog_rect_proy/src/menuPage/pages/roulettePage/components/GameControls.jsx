import React from "react";
import { motion } from "framer-motion";
import { RotateCcw } from "lucide-react";
import "../cssClases/GameControls.css";

const GameControls = ({ onSpin, onClearBets, canSpin }) => {
  return (
    <motion.div
      className="controls-container"
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.5, delay: 0.5 }}
    >
      <motion.button
        className={`spin-button ${canSpin ? "active" : "disabled"}`}
        onClick={onSpin}
        disabled={!canSpin}
        aria-disabled={!canSpin}
        whileHover={
          canSpin
            ? { scale: 1.08, boxShadow: "0 0 12px #16a34a88" }
            : {}
        }
        whileTap={canSpin ? { scale: 0.96 } : {}}
      >
        GIRAR RULETA
      </motion.button>

      <motion.button
        className="clear-button"
        onClick={onClearBets}
        whileHover={{ scale: 1.08 }}
        whileTap={{ scale: 0.96 }}
      >
        <RotateCcw className="icon" />
        REINICIAR APUESTAS
      </motion.button>
    </motion.div>
  );
};

export default GameControls;

