package com.prototype.callcenter.model;

public class Employee {

	protected int level;

	protected boolean available;
	
	public Employee(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
