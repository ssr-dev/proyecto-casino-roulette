import React from "react";
import { motion } from "framer-motion";
import { ROULETTE_NUMBERS } from "../../utils/rouletteLogic";
import "../cssClases/HistoryPanel.css";

const HistoryPanel = ({ history }) => {
  const getNumberColor = (number) => {
    const numInfo = ROULETTE_NUMBERS.find((n) => n.number === number);
    return numInfo ? numInfo.color : "gray";
  };

  return (
    <motion.div
      className="history-panel"
      initial={{ opacity: 0, x: -20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.5, delay: 0.1 }}
    >
      <h3 className="history-title">Últimos números</h3>

      <div className="history-list">
        {history.length === 0 ? (
          <p className="history-empty">Aún no hay números.</p>
        ) : (
          history.slice(0, 10).map((num, index) => (
            <motion.div
              key={index}
              className={`history-number ${getNumberColor(num)}`}
              initial={{ opacity: 0, x: -10 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 0.3, delay: index * 0.05 }}
              title={`Número ${num} (${getNumberColor(num)})`}
            >
              {num}
            </motion.div>
          ))
        )}
      </div>
    </motion.div>
  );
};

export default HistoryPanel;

