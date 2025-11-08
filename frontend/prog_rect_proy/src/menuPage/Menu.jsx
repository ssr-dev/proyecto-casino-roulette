import React from "react";
import "./Menu.css";
import Home from "./pages/Home.jsx";
import Login from "./pages/Login.jsx";
import Reglas from "./pages/Reglas.jsx";
import RouletteApp from "./pages/roulettePage/RoulettePage.jsx";
import { Routes, Route } from "react-router-dom";

function MenuApp({ user, setUser }) {
  
  return (
    <Routes>
      <Route path="/" element={<Home user={user} />} />
      <Route path="/login" element={<Login setUser={setUser} user={user} />} />
      <Route path="/reglas" element={<Reglas user = {user}/>} />
      <Route path="/roulette" element={<RouletteApp setUser={setUser} user={user} />} />
    </Routes>
  );
}


export default MenuApp;

