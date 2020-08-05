package com.gautam.dao;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gautam.model.DBUtils;
import com.gautam.model.Employee;
//import com.gautam.model.IdComparator;

public class EmployeeManagementDAOImpl implements EmployeeManagementDAO {
	
	private static EmployeeManagementDAO employeeDao;
	private EmployeeManagementDAOImpl() {
		
	}
	
	public  static EmployeeManagementDAO getInstance() {
		if(employeeDao==null) employeeDao=new EmployeeManagementDAOImpl();
		return employeeDao;
	}
	
//	List<Employee> employees=new ArrayList<>();
//	Set<Employee> employees=new HashSet<>();
//	Set<Employee> employees=new TreeSet<>();
//	Set<Employee> employees=new TreeSet<>(new IdComparator());
//	Set<Employee> employees=new TreeSet<>((e1, e2) -> {
//		return e1.getEmpId().compareTo(e2.getEmpId());
//	});
	
	public Integer addEmployee(Employee emp) throws Exception {
		String queryString="insert into employees(emp_id, f_name, l_name, salary) values(?, ?, ?, ?)";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		int result=0;
		try {
			connection=DBUtils.getConnection();
			preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setInt(1,  emp.getEmpId());
			preparedStatement.setString(2, emp.getfName());
			preparedStatement.setString(3, emp.getlName());
			preparedStatement.setInt(4, emp.getSalary());
			result=preparedStatement.executeUpdate();
			if(result>0) {
				connection.commit();
				return emp.getEmpId();
			} else {
				connection.rollback();
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return null;
//		if(employees.add(emp)) return emp.getEmpId();
//		return null;
	}
	
	public Integer updateEmployee(Integer empId, Employee newEmp) throws Exception {
		if(getEmployee(newEmp.getEmpId())!=null) throw new Exception("Primary Key Constraint Violation!");
		Employee oldEmp=getEmployee(empId);
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		int res=0;
		if(oldEmp!=null) {
			if(newEmp!=null && oldEmp.equals(newEmp)) {
				throw new Exception("Same Record");
			} else {
				try {
					Class ref=Class.forName("com.gautam.model.Employee");
					Method[] methods=ref.getDeclaredMethods();
					List<String> result=new ArrayList<>();
					List<Object> values=new ArrayList<>();
					for(Method m : methods) {
						if(m.getName().startsWith("get")) {
							Object o1=m.invoke(oldEmp);
							Object o2=m.invoke(newEmp);
							if(o1!=null && o2!=null) {
								if(o1.equals(o2)) {
									
								} else if(o1.getClass().equals(o2.getClass())) {
									if(o1!=o2) {
										result.add(m.getName());
										values.add(o2);
									}
								} else {
									
								}
							}
						}
					}
//					System.out.println(result);
					result=result.stream().map(e->e.replace("get", "")).map(e->camelToSnake(e)).collect(Collectors.toList());
//					System.out.println(result);
					StringBuffer sb=new StringBuffer("update employees set ");
					for(int i=0;i<result.size();i++) {
						if(i==result.size()-1) sb.append(result.get(i)+" = ?");
						else sb.append(result.get(i)+" = ? , ");
					}
					sb.append(" where emp_id= ?");
					String updateStatement=sb.toString();
//					System.out.println(updateStatement);
					connection=DBUtils.getConnection();
					preparedStatement=connection.prepareStatement(updateStatement);
					int i=1;
					for(Object o : values) {
						preparedStatement.setObject(i++, o);
					}
					preparedStatement.setInt(i, empId);
					res=preparedStatement.executeUpdate();
					if(res>0) {
						connection.commit();
						return newEmp.getEmpId();
					} else {
						connection.rollback();
						return null;
					}
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					DBUtils.closeConnection(connection);
				}
			}
		} else {
			throw new Exception("Employee Not Found");
		}
		return null;
//		int index=-1;
//		for(int i=0;i<employees.size();i++) {
//			if(employees.get(i).getEmpId().equals(empId)) {
//				index=i;
//				break;
//			}
//		}
//		return employees.set(index, newEmp).getEmpId();
	}
	
	public Integer deleteEmployee(Integer empId) throws Exception {
		String queryString="delete from employees where emp_id=?";
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		int result=0;
		try {
			connection=DBUtils.getConnection();
			preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setInt(1,  empId);
			result=preparedStatement.executeUpdate();
			if(result>0) {
				connection.commit();
				return empId;
			}
		} catch(SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return null;
//		for(Employee e : employees) {
//			if(e.getEmpId().equals(empId)) {
//				if(employees.remove(e)) return empId;
//			}
//		}
//		return null;
	}
	
	public Employee getEmployee(Integer empId) throws Exception {
		String queryString="select * from employees where emp_id=?";
		Employee emp=null;
		Connection connection=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try {
			connection=DBUtils.getConnection();
			preparedStatement=connection.prepareStatement(queryString);
			preparedStatement.setInt(1,  empId);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				emp=new Employee();
				emp.setEmpId(resultSet.getInt("emp_id"));
				emp.setfName(resultSet.getString("f_name"));
				emp.setlName(resultSet.getString("l_name"));
				emp.setSalary(resultSet.getInt("salary"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return emp;
//		for(Employee e : employees) {
//			if(e.getEmpId().equals(empId)) return e;
//		}
//		return null;
	}
	
	public Optional<List<Employee>> getEmployees() throws Exception {
		String queryString="select * from employees";
		List<Employee> employees=null;
		Connection connection=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=null;
		try {
			connection=DBUtils.getConnection();
			preparedStatement=connection.prepareStatement(queryString);
			resultSet=preparedStatement.executeQuery();
			int flag=0;
			while(resultSet.next()) {
				if(flag==0) {
					employees=new ArrayList<Employee>();
					flag=1;
				}
				Employee emp=new Employee();
				emp.setEmpId(resultSet.getInt("emp_id"));
				emp.setfName(resultSet.getString("f_name"));
				emp.setlName(resultSet.getString("l_name"));
				emp.setSalary(resultSet.getInt("salary"));
				employees.add(emp);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(connection);
		}
		return Optional.ofNullable(employees);
//		return new ArrayList<>(employees);
	}
	
	public static String camelToSnake(String camel) {
		StringBuffer sb=new StringBuffer();
		int flag=0;
		for(char c : camel.toCharArray()) {
			if(Character.isUpperCase(c)) {
				if(flag==0) {
					sb.append(Character.toLowerCase(c));
				} else {
					sb.append('_').append(Character.toLowerCase(c));
				}
			} else {
				sb.append(c);
			}
			flag=1;
		}
		return sb.toString();
	}
	
}
//dml executeupdate
//retriveal executequery
//