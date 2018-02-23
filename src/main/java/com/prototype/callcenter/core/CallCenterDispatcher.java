package com.prototype.callcenter.core;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prototype.callcenter.model.Employee;
import com.prototype.callcenter.model.PhoneCall;
import com.prototype.callcenter.model.PhoneCallStatus;

/**
 * The Class CallCenterDispatcher.
 */
public class CallCenterDispatcher implements Runnable {

	/** The Constant MIN_AGENT_LEVEL. */
	private static final int MIN_AGENT_LEVEL = 1;

	/** The Constant MAX_AGENT_LEVEL. */
	private static final int MAX_AGENT_LEVEL = 3;

	/** The running. */
	private volatile boolean running;

	/** The logger. */
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The incoming queue. */
	private CallsQueue<PhoneCall> incomingQueue;

	/** The executor. */
	private ExecutorService executor;

	/** The employees. */
	private List<Employee> employees;
	
	/** The attended calls. */
	private Integer attendedCalls;

	/**
	 * Instantiates a new call center dispatcher.
	 *
	 * @param employees the employees
	 * @param incomingQueue the incoming queue
	 */
	public CallCenterDispatcher(List<Employee> employees, CallsQueue<PhoneCall> incomingQueue) {

		this.employees = employees;
		this.incomingQueue = incomingQueue;
		this.executor = Executors.newFixedThreadPool(employees.size());
		this.attendedCalls = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		LOGGER.debug("Calls dispatcher started...");

		running = true;
		while (running) {

			if (Thread.interrupted()) {
				return;
			}

			PhoneCall call = incomingQueue.takeCall();
			
			if (call != null) {
				dispatchCall(call, MIN_AGENT_LEVEL);
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		LOGGER.debug("Calls dispatcher stopped.");
	}

	/**
	 * Stop running.
	 */
	public void stopRunning() {
		
		executor.shutdown();
		running = false;
	}

	/**
	 * Dispatch call.
	 *
	 * @param call the call
	 * @param level the level
	 * @return the phone call status
	 */
	public PhoneCallStatus dispatchCall(PhoneCall call, int level) {

		LOGGER.debug("Trying to dispatch {} to agent level [{}]", call, level);

		try {
			Employee agent = getAvailableAgent(level);

			LOGGER.debug("{} take the call [{}]", agent.toString(), call.getId());

			call.setStatus(PhoneCallStatus.TAKED);
			agent.assignCall(call);

			executor.submit(agent);
			
			attendedCalls++;
			
		} catch (Exception e) {
			
			LOGGER.error("error dispatching call !", e);
		}

		return call.getStatus();
	}
	
	/**
	 * Gets the available agent.
	 *
	 * @param level
	 *            the level
	 * @return the available agent
	 */
	public Employee getAvailableAgent(final int level) {

		Employee employee = employees.stream()
				.filter(e -> e.getLevel() == level)
				.filter(e -> e.isAvailable()).findAny()
				.orElse(null);

		if (employee == null) {

			int nextLevel = (level < MAX_AGENT_LEVEL) ? level + 1 : MIN_AGENT_LEVEL;
			employee = getAvailableAgent(nextLevel);
		}

		return employee;
	}

	/**
	 * Gets the attended calls.
	 *
	 * @return the attended calls
	 */
	public Integer getAttendedCalls() {
		return attendedCalls;
	}
	
	
}
