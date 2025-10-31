package com.casino.Model;

public enum BetType {
    NUMBER,     // Apuesta a un número exacto (paga 35:1)
    COLOR,      // Apuesta a un color (rojo/negro) - paga 1:1
    PAR_IMPAR,  // Apuesta a si el número será par o impar - paga 1:1
    ALTO_BAJO,  // Apuesta a rango (1–18 o 19–36) - paga 1:1
    TERCIO,     // Apuesta a tercio/docena (1–12, 13–24, 25–36) - paga 2:1
    COLUMNA,    // Apuesta a columna (1ª, 2ª o 3ª) - paga 2:1
    DOBLE,      // Apuesta doble (dos números adyacentes) - paga 17:1
    CALLE       // Apuesta a una fila de tres números consecutivos - paga 11:1
}
