package com.prototype.callcenter.model;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallCenterDispatcher {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CallCenterDispatcher.class);
	
	private CallsQueue<PhoneCall> incomingCalls;
	
	private List<Employee> employees;
	
	private ExecutorService executor;
	
	public CallCenterDispatcher(List<Employee> employees) {
		
		this.employees = employees; 
		this.incomingCalls = new CallsQueue<>();
		this.executor = Executors.newFixedThreadPool(employees.size());
	}
	
	public boolean receiveCall(PhoneCall call) {
		
		return incomingCalls.addCall(call);
	}
	
	public void startDispatcher() {
		
		LOGGER.debug("initializing calls dispatcher...");
		
		while (incomingCalls.hasAwaitingCalls()) {
			
			LOGGER.debug("finding available agent...");
			
			Employee agent = getAvailableAgent();
			
			if (agent != null) {
				
				PhoneCall call = incomingCalls.takeCall();
				
				LOGGER.debug("call [{}] taked by employee [{}]...", call.getIdentifier(), agent.getId());
			}
		}
		
		LOGGER.debug("no more awaiting calls... bye!");
		
		shutdown();
	}
	
	public void dispatchCall(Employee agent, PhoneCall call) {
		
		call.setStatus(PhoneCallStatus.ATTENDED);
		agent.assignCall(call);
		
		executor.submit(agent);		
	}
	
	public Employee getAvailableAgent() {
		
		return employees.stream().filter(e -> e.isAvailable()).findAny().orElse(null);
	}
	
	private void shutdown() {
		
		try {
			executor.shutdown();
			executor.awaitTermination(5, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
