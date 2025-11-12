import React, { useState, useEffect, useRef } from "react";
import { motion } from "framer-motion";
import { ROULETTE_NUMBERS } from "../../../../utils/rouletteLogic";
import "../cssClases/RouletteWheel.css";

const RouletteWheel = ({ onSpinEnd = () => {}, spinning, winningNumber }) => {
  const [rotation, setRotation] = useState(0);
  const previousRotation = useRef(0); // 🔹 Guarda el último ángulo de rotación
  const wheelRef = useRef(null);

  // useEffect(() => {
  //   if (spinning && winningNumber) {
  //     const targetNumberIndex = ROULETTE_NUMBERS.findIndex(
  //       (n) => n.number === winningNumber.number
  //     );
  //     if (targetNumberIndex === -1) return;

  //     const degreesPerSegment = 360 / ROULETTE_NUMBERS.length;
  //     const numberAngle = targetNumberIndex * degreesPerSegment;

  //     // 🔹 Ángulo actual en pantalla (0–360)
  //     const currentAngle = previousRotation.current % 360;

  //     // 🔹 Calculamos el ángulo para que el número ganador quede en 0°
  //     let offsetToPointer = 360 - numberAngle;

  //     // 🔹 Ajustamos tomando en cuenta el ángulo actual visible
  //     const relativeOffset = (offsetToPointer - currentAngle + 360) % 360;

  //     // 🔹 Añadimos varios giros extra (para el efecto de realismo)
  //     const extraSpins = 360 * (5 + Math.floor(Math.random() * 3));

  //     // 🔹 Nueva rotación absoluta
  //     const newRotation = previousRotation.current + extraSpins + relativeOffset;

  //     // 🔹 Guardamos el ángulo para la siguiente vez
  //     previousRotation.current = newRotation;

  //     setRotation(newRotation - 5);
  //   }
  // }, [spinning, winningNumber]);

  useEffect(() => {
    if (spinning && winningNumber !== null && winningNumber !== undefined) {
      // Acepta tanto { number: 17 } como simplemente 17
      const targetValue =
        typeof winningNumber === "object"
          ? winningNumber.number
          : winningNumber;

      const targetNumberIndex = ROULETTE_NUMBERS.findIndex(
        (n) => n.number === targetValue
      );
      if (targetNumberIndex === -1) return;

      const degreesPerSegment = 360 / ROULETTE_NUMBERS.length;
      const numberAngle = targetNumberIndex * degreesPerSegment;

      const currentAngle = previousRotation.current % 360;
      const offsetToPointer = 360 - numberAngle;
      const relativeOffset = (offsetToPointer - currentAngle + 360) % 360;
      const extraSpins = 360 * (5 + Math.floor(Math.random() * 3));

      const newRotation =
        previousRotation.current + extraSpins + relativeOffset;
      previousRotation.current = newRotation;

      // Se aplica una pequeña variación para no verse robótico
      setRotation(newRotation + Math.random() * 4 - 2);
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
        animate={{ rotate: rotation }}
        transition={{
          duration: 5, // duración del giro
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
