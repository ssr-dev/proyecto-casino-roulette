package com.casino.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SpinResult {
    private int winningNumber;
    private boolean spin;
	public SpinResult(int winningNumber, boolean spin) {
		super();
		this.winningNumber = winningNumber;
		this.spin = spin;
	}
	public int getWinningNumber() {
		return winningNumber;
	}
	public void setWinningNumber(int winningNumber) {
		this.winningNumber = winningNumber;
	}
	public boolean isSpin() {
		return spin;
	}
	public void setSpin(boolean spin) {
		this.spin = spin;
	}
    
    

}