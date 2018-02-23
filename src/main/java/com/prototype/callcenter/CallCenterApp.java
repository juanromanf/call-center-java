package com.prototype.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.prototype.callcenter.core.CallCenter;
import com.prototype.callcenter.model.Director;
import com.prototype.callcenter.model.Employee;
import com.prototype.callcenter.model.Operator;
import com.prototype.callcenter.model.PhoneCall;
import com.prototype.callcenter.model.Supervisor;

public class CallCenterApp {

	public static void main(String[] args) {

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
		
		CallCenter callCenter = new CallCenter(employees);
		
		callCenter.startDispatcher();
		
		for (int i = 1; i <= 20; i++) {
			
			callCenter.receiveCall(new PhoneCall(String.format("%02d", i)));
		}
		
		try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		callCenter.stopDispatcher();
	}

}
