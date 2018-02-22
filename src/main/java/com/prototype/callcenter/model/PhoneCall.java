package com.prototype.callcenter.model;

public class PhoneCall {

	private String id;

	private int duration;

	private PhoneCallStatus status;

	public PhoneCall(String id) {
		this.id = id;
		this.duration = 0;
		this.status = PhoneCallStatus.AWAITING;
	}

	public String getId() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public PhoneCallStatus getStatus() {
		return status;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setStatus(PhoneCallStatus status) {
		this.status = status;
	}

}
