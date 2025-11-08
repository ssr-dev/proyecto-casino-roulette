// BettingTable.jsx
import React from "react";
import { motion } from "framer-motion";
import "../cssClases/BettingTable.css";

const numberColors = {
  1: "red",
  2: "black",
  3: "red",
  4: "black",
  5: "red",
  6: "black",
  7: "red",
  8: "black",
  9: "red",
  10: "black",
  11: "black",
  12: "red",
  13: "black",
  14: "red",
  15: "black",
  16: "red",
  17: "black",
  18: "red",
  19: "red",
  20: "black",
  21: "red",
  22: "black",
  23: "red",
  24: "black",
  25: "red",
  26: "black",
  27: "red",
  28: "black",
  29: "black",
  30: "red",
  31: "black",
  32: "red",
  33: "black",
  34: "red",
  35: "black",
  36: "red",
};

const columns = [
  [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34],
  [2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35],
  [3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36],
];

const dozens = [
  { value: "1st12", label: "1st 12", range: [1, 12] },
  { value: "2nd12", label: "2nd 12", range: [13, 24] },
  { value: "3rd12", label: "3rd 12", range: [25, 36] },
];

const bottomBets = [
  { type: "range", value: "1to18", label: "1 to 18" },
  { type: "parity", value: "even", label: "EVEN" },
  { type: "color", value: "red", label: "RED" },
  { type: "color", value: "black", label: "BLACK" },
  { type: "parity", value: "odd", label: "ODD" },
  { type: "range", value: "19to36", label: "19 to 36" },
];

const BettingTable = ({ onPlaceBet, currentBets, winningNumber }) => {
  // Devuelve las clases correctas para cada celda según tipo y estado
  const getCellClasses = (type, value) => {
    let classes = "bet-cell";
    if (type === "number" && value === 0) classes += " zero-cell";
    if (type === "number" && value !== 0) classes += ` ${numberColors[value]}`;
    if (type === "dozen") classes += " dozen-cell";
    if (type === "column") classes += " column-cell";
    if (type === "range" || type === "parity" || type === "color")
      classes += " bottom-cell";
    // Highlight si es ganador
    if (winningNumber) {
      if (type === "number" && winningNumber.number === value)
        classes += " highlight";
      if (
        type === "color" &&
        winningNumber.color === value &&
        winningNumber.number !== 0
      )
        classes += " highlight";
      if (type === "parity" && winningNumber.number !== 0) {
        if (value === "even" && winningNumber.number % 2 === 0)
          classes += " highlight";
        if (value === "odd" && winningNumber.number % 2 !== 0)
          classes += " highlight";
      }
      if (type === "range" && winningNumber.number !== 0) {
        if (
          value === "1to18" &&
          winningNumber.number >= 1 &&
          winningNumber.number <= 18
        )
          classes += " highlight";
        if (
          value === "19to36" &&
          winningNumber.number >= 19 &&
          winningNumber.number <= 36
        )
          classes += " highlight";
      }
      if (type === "dozen" && winningNumber.number !== 0) {
        if (value === "1st12" && winningNumber.number <= 12)
          classes += " highlight";
        if (
          value === "2nd12" &&
          winningNumber.number >= 13 &&
          winningNumber.number <= 24
        )
          classes += " highlight";
        if (value === "3rd12" && winningNumber.number >= 25)
          classes += " highlight";
      }
      if (type === "column" && winningNumber.number !== 0) {
        if (value === "col1" && columns[0].includes(winningNumber.number))
          classes += " highlight";
        if (value === "col2" && columns[1].includes(winningNumber.number))
          classes += " highlight";
        if (value === "col3" && columns[2].includes(winningNumber.number))
          classes += " highlight";
      }
    }
    return classes;
  };

  const getBetAmount = (type, value) => {
    const bet = currentBets.find((b) => b.type === type && b.value === value);
    return bet ? bet.amount : 0;
  };

  return (
    <motion.div
      className="betting-table"
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.4 }}
    >
      <div className="bet-grid">
        {/* Zero */}
        <div className="left-column">
          <div
            className={getCellClasses("number", 0)}
            onClick={() => onPlaceBet("number", 0)}
            tabIndex={0}
            aria-label="Bet on 0"
          >
            0
            {getBetAmount("number", 0) > 0 && (
              <div className="bet-amount">{getBetAmount("number", 0)}</div>
            )}
          </div>
        </div>
        {/* Numbers grid */}
        <div className="center-column">
          <div className="numbers-grid">
            {Array.from({ length: 12 }, (_, colIndex) => {
              const colNumbers = [
                3 + colIndex * 3,
                2 + colIndex * 3,
                1 + colIndex * 3,
              ];
              return (
                <div key={colIndex} className="number-column">
                  {colNumbers.map((num) => (
                    <div
                      key={num}
                      className={getCellClasses("number", num)}
                      onClick={() => onPlaceBet("number", num)}
                      tabIndex={0}
                      aria-label={`Bet on ${num}`}
                    >
                      {num}
                      {getBetAmount("number", num) > 0 && (
                        <div className="bet-amount">{getBetAmount("number", num)}</div>
                      )}
                    </div>
                  ))}
                </div>
              );
            })}
          </div>
          {/* Dozens */}
          <div className="dozens">
            {dozens.map(({ value, label }) => (
              <div
                key={value}
                className={getCellClasses("dozen", value)}
                onClick={() => onPlaceBet("dozen", value)}
                tabIndex={0}
                aria-label={`Bet on ${label}`}
              >
                {label}
                {getBetAmount("dozen", value) > 0 && (
                  <div className="bet-amount">{getBetAmount("dozen", value)}</div>
                )}
              </div>
            ))}
          </div>
          {/* Bottom bets */}
          <div className="bottom-bets">
            {bottomBets.map(({ type, value, label }) => (
              <div
                key={value}
                className={getCellClasses(type, value)}
                onClick={() => onPlaceBet(type, value)}
                tabIndex={0}
                aria-label={`Bet on ${label}`}
              >
                {label}
                {getBetAmount(type, value) > 0 && (
                  <div className="bet-amount">{getBetAmount(type, value)}</div>
                )}
              </div>
            ))}
          </div>
        </div>
        {/* Columns */}
        <div className="right-column">
          {["col3", "col2", "col1"].map((col, idx) => (
            <div
              key={col}
              className={getCellClasses("column", col)}
              onClick={() => onPlaceBet("column", col)}
              tabIndex={0}
              aria-label={`Bet on column ${idx + 1}`}
            >
              2:1
              {getBetAmount("column", col) > 0 && (
                <div className="bet-amount">{getBetAmount("column", col)}</div>
              )}
            </div>
          ))}
        </div>
      </div>
    </motion.div>
  );
};

export default BettingTable;
