package com.casino.dtos;


public class SpinResult {
    private boolean spin;
    private int winningNumber;
    private boolean isWinner; 
    
    private Long userId;        
    private double newBalance;  
    
    public SpinResult(Long userId, double newBalance, int winningNumber, boolean isWinner) {
        this.userId = userId;
        this.newBalance = newBalance;
        this.winningNumber = winningNumber;
        this.isWinner = isWinner;
        this.spin = true; 
    }

    public SpinResult() {
        this.spin = false; 
    }

	public boolean isSpin() {
		return spin;
	}

	public void setSpin(boolean spin) {
		this.spin = spin;
	}

	public int getWinningNumber() {
		return winningNumber;
	}

	public void setWinningNumber(int winningNumber) {
		this.winningNumber = winningNumber;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}

    
}