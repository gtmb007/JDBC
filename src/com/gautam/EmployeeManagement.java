package com.gautam;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.gautam.model.Employee;
import com.gautam.service.EmployeeManagementService;
import com.gautam.service.EmployeeManagementServiceImpl;

public class EmployeeManagement {
	EmployeeManagementService employeeService=EmployeeManagementServiceImpl.getInstance();
	static Scanner sc=new Scanner(System.in);
	public  static void main(String[] args) {
		EmployeeManagement obj=new EmployeeManagement();
		while(true) {
			System.out.println("Enter \n 1.) Add \n 2.) Update \n 3.) Delete \n 4.) Get By Id \n 5.) Get All Employees\n 0.) Exit \n");
			int option=sc.nextInt();
			switch(option) {
				case 1: obj.addEmployee();
				break;
				case 2 : obj.updateEmployee();
				break;
				case 3 : obj.deleteEmployee();
				break;
				case 4 : obj.getEmployee();
				break;
				case 5 : obj.getEmployees();
				break;
				case 0 : System.exit(0);;
				default :
					System.out.println("Select Proper Choice");
			}
		}
	}
	
	public void addEmployee() {
		Employee emp=new Employee();
		emp.setEmpId(1);
		emp.setfName("Gautam");
		emp.setlName("Bharadwaj");
		emp.setSalary(50000);
		try {
			Integer id=employeeService.addEmployee(emp);
			System.out.println("Employee Added Successfully with id : "+id);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void updateEmployee() {
		Employee emp=new Employee();
		emp.setEmpId(1);
		emp.setfName("Gautam");
		emp.setlName("Bharadwaj");
		emp.setSalary(60000);
		try {
			Integer id=employeeService.updateEmployee(1, emp);
			System.out.println("Employee Updated Successfully with id : "+id);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteEmployee() {
		try {
			Integer id=employeeService.deleteEmployee(1);
			System.out.println("Employee Deleted Successfully with id : "+id);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getEmployee() {
		try {
			Employee emp=employeeService.getEmployee(1);
			System.out.println(emp);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getEmployees() {
		try {
			Optional<List<Employee>> opEmpList=employeeService.getEmployees();
			if(opEmpList.isPresent()) {
				List<Employee> empList=opEmpList.get();
				for(Employee e : empList)
					System.out.println(e);
			} else {
				System.out.println("No Employees Found!");
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
