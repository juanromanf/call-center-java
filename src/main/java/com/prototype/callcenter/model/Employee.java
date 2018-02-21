package com.prototype.callcenter.model;

import java.util.concurrent.TimeUnit;

public class Employee implements Runnable {

	protected String id;

	protected int level;

	protected boolean available;
	
	protected PhoneCall call;

	public Employee(String id, int level) {
		this.id = id;
		this.level = level;
	}

	public String getId() {
		return id;
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
	
	public void assignCall(PhoneCall call) {
		
		this.call = call;
		this.available = false;
	}

	@Override
	public void run() {
		
		try {
			int duration = 10;
			call.setDuration(duration);
			
			TimeUnit.SECONDS.sleep(duration);
			
			this.setAvailable(true);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
