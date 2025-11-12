import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./index.css";

import MenuApp from "./menuPage/App.jsx";
import RouletteApp from "./roulettePage/App.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        {/* Rutas del menú principal */}
        <Route path="/*" element={<MenuApp />} />

        {/* Ruta del juego */}
        <Route path="/roulette" element={<RouletteApp />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
