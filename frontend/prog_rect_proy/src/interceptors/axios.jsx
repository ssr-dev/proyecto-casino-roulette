import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // 🔗 Reemplaza con tu API real
  timeout: 10000,
});

// =======================
// 🔒 Interceptores
// =======================
api.interceptors.request.use(
  (config) => {
    // Aquí puedes añadir headers (token JWT, etc.)
    console.log(
      "➡️ Enviando petición:",
      config.method?.toUpperCase(),
      config.url
    );
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => {
    console.log("✅ Respuesta recibida:", response.config.url);
    return response.data;
  },
  (error) => {
    console.error("X Error en la respuesta:", error);
    if (error.response) {
      console.error("Detalles:", error.response.data);
    }
    return Promise.reject(error);
  }
);

// =======================
// 🎰 Funciones API REST
// =======================

// Crear usuario
export const createUser = (userData) => api.post("/users", userData);

// Obtener usuario por ID
// funcionalidad pendiente
export const getUser = (userName) => api.get(`/users/${userName}`);

// obtener numero ganador, esta señal se generara cada cierto tiempo
export const getWinningNumber = () => api.get("/roulette/spin");

// se hace el envio tanto de las apuestas realizadas en la ronda
export const postBets = (userId, bets) =>
  api.post(`/roulette/bet/${userId}`, bets);

// Historial de numeros que han salido en la ruleta
export const getHistory = () => api.get("/roulette/history");

// Números Hot (más frecuentes)
export const getHotNumbers = () => api.get("/roulette/stats/hot");

// Números Cold (menos frecuentes)
export const getColdNumbers = () => api.get("/roulette/stats/cold");

// =======================
// 🧩 Exportación principal
// =======================
const RouletteAPI = {
  createUser,
  getUser,
  postBets,
  getWinningNumber,
  getHistory,
  getHotNumbers,
  getColdNumbers,
};

export default RouletteAPI;
