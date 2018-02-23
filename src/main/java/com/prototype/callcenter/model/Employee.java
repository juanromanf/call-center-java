package com.prototype.callcenter.model;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prototype.callcenter.utils.RandomGeneratorUtil;

/**
 * The Employee of the CallCenter.
 */
public class Employee implements Callable<PhoneCall> {

	/** The logger. */
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The id. */
	protected String id;

	/** The level. */
	protected int level;

	/** The available. */
	protected boolean available;

	/** The call. */
	protected PhoneCall call;

	/**
	 * Instantiates a new employee.
	 *
	 * @param id the id
	 * @param level the level
	 */
	public Employee(String id, int level) {
		this.id = id;
		this.level = level;
		this.available = true;
		this.call = null;
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
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Sets the available.
	 *
	 * @param available the new available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * Gets the assigned call.
	 *
	 * @return the call
	 */
	public PhoneCall getCall() {
		return call;
	}

	/**
	 * Assign a call.
	 *
	 * @param call the call
	 */
	public void assignCall(PhoneCall call) {

		this.call = call;
		this.available = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", level=" + level + "]";
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public PhoneCall call() throws Exception {

		try {
			int duration = RandomGeneratorUtil.getRandomNumberInRange(5, 10);
			getCall().setDuration(duration);

			TimeUnit.SECONDS.sleep(duration);
			
			getCall().setStatus(PhoneCallStatus.FINISHED);

			LOGGER.debug("{} attended the call [{}], total time [{} secs]", toString(), getCall().getId(),
					getCall().getDuration());

		} catch (InterruptedException e) {

			LOGGER.error("agent call lost!", e);

		} finally {

			setAvailable(true);
		}
		
		return getCall();
	}

}
