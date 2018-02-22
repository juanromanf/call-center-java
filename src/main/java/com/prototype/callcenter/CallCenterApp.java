package com.prototype.callcenter;

import java.util.ArrayList;
import java.util.List;

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
		
		employees.add(new Supervisor("01"));
		employees.add(new Supervisor("02"));
		
		employees.add(new Director("01"));
		
		CallCenterDispatcher dispatcher = new CallCenterDispatcher(employees, 10);
		
		for (int i = 1; i <= 20; i++) {
			
			dispatcher.dispatchCall(new PhoneCall(String.format("%02d", i)));
		}
		
		dispatcher.waitForUnattendedCalls();
	}

}
