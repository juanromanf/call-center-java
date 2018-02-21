package com.prototype.callcenter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CallCenterDispatcher {
	
	private ConcurrentLinkedQueue<PhoneCall> callQueue = new ConcurrentLinkedQueue<>();
	
	private List<Employee> employees = new ArrayList<>();
	
	public CallCenterDispatcher() {
		
	}
	
	public void receiveCall(PhoneCall call) {
		
		callQueue.offer(call);
	}
	
	public void dispatchCall() {
		
		
	}

}
