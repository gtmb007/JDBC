package com.gautam.model;

public class Employee /*implements Comparable<Employee>*/ {
	
	private Integer empId;
	private String fName;
	private String lName;
	private Integer salary;
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	
//	@Override
//	public int hashCode() {
//		return this.empId.hashCode();
//	}
//	
	@Override
	public boolean equals(Object o) {
		Employee e;
		if(o instanceof Employee) {
			e=(Employee)o;
			if(this.empId.equals(e.getEmpId()) && this.fName.equals(e.getfName()) && this.lName.equals(e.getlName()) && this.salary.equals(e.getSalary())) return true;
			return false;
		}
		else return false;
	}
	
//	@Override
//	public int compareTo(Employee e) {
//		return this.empId.compareTo(e.getEmpId());
//	}
	
	@Override
	public String toString() {
		return "EmpID: "+this.empId+" First Name: "+this.fName+" Last Name: "+this.lName+" Salary: "+this.salary;
	}
}
