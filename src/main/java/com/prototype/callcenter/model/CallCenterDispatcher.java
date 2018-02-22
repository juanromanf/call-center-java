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

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CallCenterDispatcher.class);
	
	/** The incoming calls. */
	private CallsQueue<PhoneCall> incomingCalls;
	
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
		this.incomingCalls = new CallsQueue<>();
		this.executor = Executors.newFixedThreadPool(capacity);
	}
	
	/**
	 * Receive call.
	 *
	 * @param call the call
	 * @return true, if successful
	 */
	public boolean receiveCall(PhoneCall call) {
		
		LOGGER.debug("Incoming call [{}] queued...", call.getId());
		
		return incomingCalls.addCall(call);
	}
	
	/**
	 * Start dispatcher.
	 */
	public void startDispatcher() {
		
		LOGGER.debug("Initializing calls dispatcher...");
		
		int level = 1;
		
		while (incomingCalls.hasAwaitingCalls()) {
			
			Employee agent = getAvailableAgent(level);
			
			if (agent == null) {
				
				level = (level < MAX_AGENT_LEVEL) ? level + 1 : 1;
				
			} else {
				
				dispatchCall(agent, incomingCalls.takeCall());
			}
		}
		
		stopDispatcher();
		
		LOGGER.debug("No more awaiting calls... Bye!");
	}
	
	/**
	 * Dispatch call.
	 *
	 * @param agent the agent
	 * @param call the call
	 */
	public void dispatchCall(Employee agent, PhoneCall call) {
		
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
