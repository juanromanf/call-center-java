package com.prototype.callcenter.model;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prototype.callcenter.utils.RandomGeneratorUtil;

public class Employee implements Callable<PhoneCall> {

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	protected String id;

	protected int level;

	protected boolean available;

	protected PhoneCall call;

	public Employee(String id, int level) {
		this.id = id;
		this.level = level;
		this.available = true;
		this.call = null;
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

	public PhoneCall getCall() {
		return call;
	}

	public void assignCall(PhoneCall call) {

		this.call = call;
		this.available = false;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", level=" + level + "]";
	}

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
