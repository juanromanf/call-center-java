package com.prototype.callcenter.model;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CallCenterDispatcher.
 */
public class CallCenterDispatcher {
	
	private static final int MAX_AGENT_LEVEL = 4;

	/** The LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallCenterDispatcher.class);
	
	/** The incoming calls. */
	private CallsQueue<PhoneCall> incomingCallsQueue;
	
	/** The employees. */
	private List<Employee> employees;
	
	/** The executor. */
	private ExecutorService executor;
	
	/**
	 * Instantiates a new call center dispatcher.
	 *
	 * @param employees the employees
	 * @param capacity the maximum capacity for simultaneous calls.
	 */
	public CallCenterDispatcher(List<Employee> employees, int capacity) {
		
		this.employees = employees; 
		this.incomingCallsQueue = new CallsQueue<>();
		this.executor = Executors.newFixedThreadPool(capacity);
	}
	
	/**
	 * Dispatch call.
	 *
	 * @param call the call
	 */
	public void dispatchCall(PhoneCall call) {
		
		LOGGER.debug("Incoming call [{}]...", call.getId());
		
		dispatchCall(call, 1);
	}
	
	/**
	 * Dispatch call.
	 *
	 * @param call the call
	 * @param level the level
	 */
	public void dispatchCall(PhoneCall call, int level) {
		
		Employee agent = getAvailableAgent(level);
		
		if (agent == null) {
			
			if (level < MAX_AGENT_LEVEL) {

				// try to reach next level agent
				dispatchCall(call, level + 1);
			}
			else {
				incomingCallsQueue.addCall(call);
			}

		} else {

			assignCallToAgent(agent, call);
		}
	}
	
	/**
	 * Wait for unattended calls.
	 */
	public void waitForUnattendedCalls() {
		
		LOGGER.debug("Waiting for all awaiting calls get attended...");
		
		while (incomingCallsQueue.hasAwaitingCalls()) {
			
			dispatchCall(incomingCallsQueue.takeCall(), 1);
		}
		
		stopDispatcher();
		
		LOGGER.debug("No more awaiting calls... Bye!");
	}
	
	/**
	 * Assign call to agent.
	 *
	 * @param agent the agent
	 * @param call the call
	 */
	public void assignCallToAgent(Employee agent, PhoneCall call) {
		
		call.setStatus(PhoneCallStatus.TAKED);
		agent.assignCall(call);
		
		executor.submit(agent);
	}
	
	/**
	 * Gets the available agent.
	 *
	 * @param level the level
	 * @return the available agent
	 */
	public Employee getAvailableAgent(final int level) {
		
		Employee employee = employees.stream()
				.filter(e -> e.getLevel() == level)
				.filter(e -> e.isAvailable())
				.findAny().orElse(null);
		
		return employee;
	}
	
	/**
	 * Shutdown dispatcher.
	 */
	public void stopDispatcher() {
		
		try {
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
