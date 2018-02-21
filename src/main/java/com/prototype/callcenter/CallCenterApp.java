package com.prototype.callcenter;

import java.util.ArrayList;
import java.util.List;

import com.prototype.callcenter.model.CallCenterDispatcher;
import com.prototype.callcenter.model.Employee;
import com.prototype.callcenter.model.Operator;
import com.prototype.callcenter.model.PhoneCall;

public class CallCenterApp {

	public static void main(String[] args) {

		List<Employee> employees = new ArrayList<>();
		employees.add(new Operator("operator-01"));
		employees.add(new Operator("operator-02"));
		employees.add(new Operator("operator-03"));
		
		CallCenterDispatcher dispatcher = new CallCenterDispatcher(employees);
		
		for(int i = 1; i<= 10; i++) {
			
			dispatcher.receiveCall(new PhoneCall("call-" + i));
		}
		
		dispatcher.startDispatcher();
	}

}
