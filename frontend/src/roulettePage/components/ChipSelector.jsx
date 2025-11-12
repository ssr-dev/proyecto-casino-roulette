import React from "react";
import { motion } from "framer-motion";
import "../cssClases/ChipSelector.css";

const chips = [
  { value: 1, color: "gray", text: "dark" },
  { value: 2, color: "red", text: "light" },
  { value: 5, color: "blue", text: "light" },
  { value: 10, color: "green", text: "light" },
  { value: 25, color: "purple", text: "light" },
  { value: 50, color: "yellow", text: "dark" },
  { value: 100, color: "black", text: "light" },
];

const ChipSelector = ({ selectedChip, onSelectChip }) => {
  return (
    <motion.div
      className="chip-selector-container"
      initial={{ opacity: 0, x: 20 }}
      animate={{ opacity: 1, x: 0 }}
      transition={{ duration: 0.5, delay: 0.4 }}
    >
      {chips.map((chip) => (
        <motion.button
          key={chip.value}
          className={`chip-button chip-${chip.color} chip-text-${chip.text} ${
            selectedChip === chip.value ? "chip-selected" : ""
          }`}
          onClick={() => onSelectChip(chip.value)}
          aria-pressed={selectedChip === chip.value}
          whileHover={{ scale: 1.08 }}
          whileTap={{ scale: 0.96 }}
          tabIndex={0}
        >
          <div className="chip-glow"></div>
          <span className="chip-label">{chip.value}</span>
        </motion.button>
      ))}
    </motion.div>
  );
};

export default ChipSelector;

