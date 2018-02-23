package com.prototype.callcenter.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.prototype.callcenter.core.CallCenter;
import com.prototype.callcenter.model.Director;
import com.prototype.callcenter.model.Employee;
import com.prototype.callcenter.model.Operator;
import com.prototype.callcenter.model.PhoneCall;
import com.prototype.callcenter.model.Supervisor;

/**
 * The test cases for {@link CallCenter} class.
 */
public class CallCenterTest {

	/** The dispatcher. */
	private CallCenter callCenter;

	/**
	 * SetsUp.
	 */
	@BeforeClass
	public void setUp() {

		callCenter = new CallCenter(buildEmployeesList());
		callCenter.startDispatcher();
	}

	/**
	 * All received calls should be attended.
	 */
	@Test
	public void allReceivedCallsShouldBeAttended() {

		for (int i = 1; i <= 10; i++) {

			callCenter.receiveCall(new PhoneCall(String.format("%02d", i)));
		}
		
		// wait for calls completion
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) { 
			//nothing
		}
		
		Assert.assertEquals(callCenter.getDispatcher().getAttendedCalls(), new Integer(10));
	}

	/**
	 * Shutdown.
	 */
	@AfterClass
	public void shutdown() {

		callCenter.stopDispatcher();
	}

	/**
	 * Builds the employees list.
	 *
	 * @return the list
	 */
	private List<Employee> buildEmployeesList() {

		List<Employee> employees = new ArrayList<>();
		employees.add(new Operator("01"));
		employees.add(new Operator("02"));
		employees.add(new Operator("03"));
		employees.add(new Operator("04"));
		employees.add(new Operator("05"));
		employees.add(new Operator("06"));
		employees.add(new Operator("07"));

		employees.add(new Supervisor("01"));
		employees.add(new Supervisor("02"));

		employees.add(new Director("01"));

		return employees;
	}

}
