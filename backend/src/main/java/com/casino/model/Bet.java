package com.casino.model;

import lombok.Data;

@Data
public class Bet {

    private User user;
    private double amount;
    private BetType type;
    private Integer number; 
    private String color;   
    private Integer tercio;
    private Integer columna;
    private String parImpar;
    private String altoBajo;

    public Bet(User user,double amount, BetType type) {
        this.user = user;
        this.amount = amount;
        this.type = type;
    }

    // No más constructores específicos - usaremos solo setters desde la clase User

    public boolean isWinner(int winningNumber) {
        if (winningNumber == 0) {
            // El 0 solo gana en apuestas directas al número 0
            return type == BetType.NUMBER && number != null && number == 0;
        }

        switch (type) {
            case NUMBER:
                return number != null && number == winningNumber;

            case COLOR:
                String winningColor = getColor(winningNumber);
                return color != null && color.equalsIgnoreCase(winningColor);

            case DOZEN:
                int tercioGanador = getTercio(winningNumber);
                return tercio != null && tercio == tercioGanador;

            case COLUMN:
                int columnaGanadora = getColumna(winningNumber);
                return columna != null && columna == columnaGanadora;

            case PARITY:
                String resultadoParImpar = (winningNumber % 2 == 0) ? "PAR" : "IMPAR";
                return parImpar != null && parImpar.equalsIgnoreCase(resultadoParImpar);

            case RANGE:
                String resultadoAltoBajo = (winningNumber <= 18) ? "BAJO" : "ALTO";
                return altoBajo != null && altoBajo.equalsIgnoreCase(resultadoAltoBajo);

            default:
                return false;
        }
    }

    public double calculatePayout(int winningNumber) {
        if (!isWinner(winningNumber)) return 0;

        switch (type) {
            case NUMBER:
                return amount * 36;

            case COLOR:
            case PARITY:
            case RANGE:
                return amount * 2;  // paga 1:1 + apuesta

            case DOZEN:
            case COLUMN:
                return amount * 3;  // paga 2:1 + apuesta

            default:
                return 0;
        }
    }

    private String getColor(int number) {
        if (number == 0) return "VERDE";
        return (number % 2 == 0) ? "NEGRO" : "ROJO";
    }

    private int getTercio(int number) {
        if (number >= 1 && number <= 12) return 1;
        if (number >= 13 && number <= 24) return 2;
        return 3;
    }

    private int getColumna(int number) {
        if (number % 3 == 1) return 1;
        if (number % 3 == 2) return 2;
        return 3;
    }

	public void setUser(User user) {
		this.user = user;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setType(BetType type) {
		this.type = type;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setColor(String color) {
		this.color = color;
	}


	public void setTercio(Integer tercio) {
		this.tercio = tercio;
	}
	public void setColumna(Integer columna) {
		this.columna = columna;
	}


	public void setParImpar(String parImpar) {
		this.parImpar = parImpar;
	}


	public void setAltoBajo(String altoBajo) {
		this.altoBajo = altoBajo;
	}

	public Integer getNumber() {
	    return this.number;
	}

	public String getColor() {
	    return this.color;
	}

	public Integer getTercio() {
	    return this.tercio;
	}

	public Integer getColumna() {
	    return this.columna;
	}

	public String getParImpar() {
	    return this.parImpar;
	}

	public String getAltoBajo() {
	    return this.altoBajo;
	}

	public double getAmount() {
	    return this.amount;
	}
	public BetType getType() {
	    return this.type;
	}
    
    
}