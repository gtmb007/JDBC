package com.gautam.service;

import java.util.List;
import java.util.Optional;

import com.gautam.dao.EmployeeManagementDAO;
import com.gautam.dao.EmployeeManagementDAOImpl;
import com.gautam.model.Employee;

public class EmployeeManagementServiceImpl implements EmployeeManagementService {
	
	private static EmployeeManagementService employeeService;
	private EmployeeManagementServiceImpl() {
		
	}
	
	public static EmployeeManagementService getInstance() {
		synchronized (EmployeeManagementServiceImpl.class) {
			if(employeeService==null) employeeService=new EmployeeManagementServiceImpl();
			return employeeService;
		}
	}
	
	EmployeeManagementDAO employeeDao=EmployeeManagementDAOImpl.getInstance(); 
	
	public Integer addEmployee(Employee emp) throws Exception {
		Integer empId=employeeDao.addEmployee(emp);
		if(empId==null) throw new Exception("Employee Not Added");
		return empId;
	}
	
	public Integer deleteEmployee(Integer empId) throws Exception {
		Integer id=employeeDao.deleteEmployee(empId);
		if(id==null) throw new Exception("Employee Not Deleted");
		return id;
	}
	
	public Integer updateEmployee(Integer empId, Employee newEmp) throws Exception {
		Integer id=employeeDao.updateEmployee(empId, newEmp);
		if(id==null) throw new Exception("Employee Not Update");
		return empId;
	}
	
	public Employee getEmployee(Integer empId) throws Exception {
		Employee emp=employeeDao.getEmployee(empId);
		if(emp==null) throw new Exception("Employee Not Found");
		return emp;
	}
	
	public Optional<List<Employee>> getEmployees() throws Exception {
		Optional<List<Employee>> empList=employeeDao.getEmployees();
		if(empList==null) throw new Exception("No Employees Found");
		return empList;
	}
	
}
