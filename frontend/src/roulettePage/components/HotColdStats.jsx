import React, { useMemo } from "react";
import "../cssClases/HotColdStats.css";
import { ROULETTE_NUMBERS } from "../../utils/rouletteLogic";

const HotColdStats = ({ history }) => {
  const getNumberColor = (number) => {
    const numInfo = ROULETTE_NUMBERS.find((n) => n.number === number);
    return numInfo ? numInfo.color : "gray";
  };

  // Calcular los conteos de aparición
  const { hotNumbers, coldNumbers } = useMemo(() => {
    const counts = {};
    history.forEach((num) => {
      counts[num] = (counts[num] || 0) + 1;
    });
    const sorted = Object.entries(counts)
      .sort((a, b) => b[1] - a[1])
      .map(([num]) => parseInt(num));
    return {
      hotNumbers: sorted.slice(0, 3),
      coldNumbers: sorted.slice(-3).reverse(),
    };
  }, [history]);

  return (
    <div className="hotcold-panel">
      {/* <div className="hotcold-title">🔥 Números Calientes / ❄️ Fríos</div> */}
      <div className="hotcold-section hot-section">
        <div className="hotcold-subtitle">Calientes</div>
        <div className="hotcold-list">
          {hotNumbers.length === 0 ? (
            <span className="hotcold-empty">Sin datos</span>
          ) : (
            hotNumbers.map((num) => (
              <span
                key={num}
                className={`hotcold-number ${getNumberColor(num)}`}
                title={`Número ${num} (${getNumberColor(num)})`}
              >
                {num}
              </span>
            ))
          )}
        </div>
      </div>
      <div className="hotcold-section cold-section">
        <div className="hotcold-subtitle">Fríos</div>
        <div className="hotcold-list">
          {coldNumbers.length === 0 ? (
            <span className="hotcold-empty">Sin datos</span>
          ) : (
            coldNumbers.map((num) => (
              <span
                key={num}
                className={`hotcold-number ${getNumberColor(num)}`}
                title={`Número ${num} (${getNumberColor(num)})`}
              >
                {num}
              </span>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default HotColdStats;

