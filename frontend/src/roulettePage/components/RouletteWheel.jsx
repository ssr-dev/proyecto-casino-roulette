import React, { useState, useEffect, useRef } from "react";
import { motion } from "framer-motion";
import { ROULETTE_NUMBERS } from "../../utils/rouletteLogic";
import "../cssClases/RouletteWheel.css";

const RouletteWheel = ({ onSpinEnd = () => {}, spinning, winningNumber }) => {
  const [rotation, setRotation] = useState(0);
  const wheelRef = useRef(null);

  useEffect(() => {
    if (spinning && winningNumber) {
      const targetNumberIndex = ROULETTE_NUMBERS.findIndex(
        (n) => n.number === winningNumber.number
      );
      if (targetNumberIndex === -1) return;

      const degreesPerSegment = 360 / ROULETTE_NUMBERS.length;
      const targetRotation = targetNumberIndex * degreesPerSegment + 360 * 5; // 5 giros completos

      setRotation(targetRotation);
    }
  }, [spinning, winningNumber]);

  const handleAnimationComplete = () => {
    if (spinning) {
      onSpinEnd();
    }
  };

  return (
    <div className="roulette-container">
      <motion.div
        ref={wheelRef}
        className="roulette-wheel"
        style={{
          backgroundImage: `conic-gradient(
            ${ROULETTE_NUMBERS.map((n, i) => {
              const start = (i / ROULETTE_NUMBERS.length) * 360;
              const end = ((i + 1) / ROULETTE_NUMBERS.length) * 360;
              const color =
                n.color === "red"
                  ? "#EF4444"
                  : n.color === "black"
                  ? "#1F2937"
                  : "#22C55E";
              return `${color} ${start}deg ${end}deg`;
            }).join(", ")}
          )`,
        }}
        animate={{ rotate: spinning ? rotation : 0 }}
        transition={{
          type: "spring",
          stiffness: 50,
          damping: 20,
          duration: spinning ? 5 : 0,
          ease: "easeOut",
        }}
        onAnimationComplete={handleAnimationComplete}
      >
        {ROULETTE_NUMBERS.map((n, i) => {
          const degreesPerSegment = 360 / ROULETTE_NUMBERS.length;
          const rotationOffset = degreesPerSegment / 2;
          const textRotation = i * degreesPerSegment + rotationOffset;
          return (
            <div
              key={n.number}
              className="roulette-number"
              style={{
                transform: `rotate(${textRotation}deg) translate(0, -45%)`,
              }}
            >
              <span className="roulette-number-text">{n.number}</span>
            </div>
          );
        })}
      </motion.div>

      <div className="roulette-center">
        <div className="roulette-core"></div>
      </div>

      <div className="roulette-pointer"></div>
    </div>
  );
};

export default RouletteWheel;

