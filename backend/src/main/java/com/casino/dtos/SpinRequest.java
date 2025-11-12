package com.casino.dtos;

//import lombok.Data;

//@Data
public class SpinRequest {
    private Long userId;
    private String type; // "number", "color", "parity", etc.
    private String value; // "red", "17", "odd", "list12", etc.
    private double amount;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
    
    
}