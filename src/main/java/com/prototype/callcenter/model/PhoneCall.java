package com.prototype.callcenter.model;

public class PhoneCall {

	private String identifier;

	private int duration;

	private PhoneCallStatus status;

	public PhoneCall(String identifier) {
		this.identifier = identifier;
		this.duration = 0;
		this.status = PhoneCallStatus.NEW;
	}

	public String getIdentifier() {
		return identifier;
	}

	public int getDuration() {
		return duration;
	}

	public PhoneCallStatus getStatus() {
		return status;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setStatus(PhoneCallStatus status) {
		this.status = status;
	}

}
