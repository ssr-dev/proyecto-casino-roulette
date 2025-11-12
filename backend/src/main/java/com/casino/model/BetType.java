package com.casino.model;

public enum BetType {
    NUMBER,   // Apuesta a un número específico (paga 35:1)
    COLOR,    // Apuesta al color (rojo/negro) - paga 1:1
    PARITY,   // Apuesta a par o impar - paga 1:1
    RANGE,    // Apuesta al rango (1–18 o 19–36) - paga 1:1
    DOZEN,    // Apuesta a docena (1st12, 2nd12, 3rd12) - paga 2:1
    COLUMN;    // Apuesta a columna (col1, col2, col3) - paga 2:1

    public static BetType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de apuesta no puede ser nulo o vacío");
        }

        switch (value.trim().toUpperCase()) {
            case "NUMBER":
                return NUMBER;
            case "COLOR":
                return COLOR;
            case "PARITY":
                return PARITY;
            case "RANGE":
                return RANGE;
            case "DOZEN":
                return DOZEN;
            case "COLUMN":
                return COLUMN;
            default:
                throw new IllegalArgumentException("Tipo de apuesta desconocido: " + value);
        }
    }
}