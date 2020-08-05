package com.gautam.dao;

import java.util.List;
import java.util.Optional;

import com.gautam.model.Employee;

public interface EmployeeManagementDAO {
	
	public Integer addEmployee(Employee emp) throws Exception;
	public Integer updateEmployee(Integer empId, Employee newEmp) throws Exception;
	public Integer deleteEmployee(Integer empId) throws Exception;
	public Employee getEmployee(Integer empId) throws Exception;
	public Optional<List<Employee>> getEmployees() throws Exception;
	
}
