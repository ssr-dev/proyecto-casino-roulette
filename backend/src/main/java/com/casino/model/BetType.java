package com.casino.model;

public enum BetType {
    NUMBER,   // Apuesta a un número específico (paga 35:1)
    COLOR,    // Apuesta al color (rojo/negro) - paga 1:1
    PARITY,   // Apuesta a par o impar - paga 1:1
    RANGE,    // Apuesta al rango (1–18 o 19–36) - paga 1:1
    DOZEN,    // Apuesta a docena (1st12, 2nd12, 3rd12) - paga 2:1
    COLUMN    // Apuesta a columna (col1, col2, col3) - paga 2:1
}