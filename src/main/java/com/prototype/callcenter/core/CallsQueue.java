package com.prototype.callcenter.core;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The CallsQueue.
 *
 * @param <T> the generic type
 */
public class CallsQueue<T>  {
	
	/** The call queue. */
	private ConcurrentLinkedQueue<T> callQueue;

	/**
	 * Instantiates a new calls queue.
	 */
	public CallsQueue() {
		
		callQueue = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * Gets the call queue.
	 *
	 * @return the call queue
	 */
	public ConcurrentLinkedQueue<T> getCallQueue() {

		return callQueue;
	}

	/**
	 * Put call.
	 *
	 * @param call the call
	 * @return true, if successful
	 */
	public boolean putCall(T call) {

		return callQueue.offer(call);
	}
	
	/**
	 * Checks for awaiting calls.
	 *
	 * @return true, if successful
	 */
	public boolean hasAwaitingCalls() {
		
		return !callQueue.isEmpty();
	}
	
	/**
	 * Next call.
	 *
	 * @return the t
	 */
	public T nextCall() {
		
		return callQueue.peek();
	}
	
	/**
	 * Take call from queue.
	 *
	 * @return the t
	 */
	public T takeCall() {
		
		return callQueue.poll();
	}
}
