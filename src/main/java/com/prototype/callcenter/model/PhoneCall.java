package com.prototype.callcenter.model;

/**
 * The PhoneCall in the CallCenter.
 */
public class PhoneCall {

	/** The id. */
	private String id;
	
	/** The duration. */
	private int duration;

	/** The status. */
	private PhoneCallStatus status;

	/**
	 * Instantiates a new phone call.
	 *
	 * @param id the id
	 */
	public PhoneCall(String id) {
		this.id = id;
		this.duration = 0;
		this.status = PhoneCallStatus.AWAITING;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public PhoneCallStatus getStatus() {
		return status;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(PhoneCallStatus status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "PhoneCall [id=" + id + "]";
	}

}
