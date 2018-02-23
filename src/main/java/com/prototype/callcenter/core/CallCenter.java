package com.prototype.callcenter.core;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prototype.callcenter.model.Employee;
import com.prototype.callcenter.model.PhoneCall;

/**
 * The CallCenter.
 */
public class CallCenter {

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallCenter.class);

	/** The incoming calls. */
	private CallsQueue<PhoneCall> incomingQueue;
	
	/** The dispatcher. */
	private CallCenterDispatcher dispatcher;

	/** The employees. */
	private List<Employee> employees;
	
	/** The executor. */
	private ExecutorService executor;

	/**
	 * Instantiates a new call center.
	 *
	 * @param employees
	 *            the employees
	 */
	public CallCenter(List<Employee> employees) {

		this.employees = employees;
		this.incomingQueue = new CallsQueue<>();
		this.executor = Executors.newSingleThreadExecutor();
	}

	/**
	 * Gets the dispatcher.
	 *
	 * @return the dispatcher
	 */
	public CallCenterDispatcher getDispatcher() {
		
		return dispatcher;
	}

	/**
	 * Start the asynchronous dispatcher.
	 */
	public void startDispatcher() {
		
		LOGGER.debug("Call center is open... Hi !");
		
		dispatcher = new CallCenterDispatcher(employees, incomingQueue);
		executor.submit(dispatcher);
	}
	
	/**
	 * Receive call and put in the incoming queue (Producer)
	 *
	 * @param call the call
	 */
	public void receiveCall(PhoneCall call) {
		
		incomingQueue.putCall(call);
	}


	/**
	 * Shutdown dispatcher and close call center.
	 */
	public void stopDispatcher() {
		
		dispatcher.stopRunning();
		executor.shutdown();
		
		LOGGER.debug("Call center is closed... Bye !");
	}

}
