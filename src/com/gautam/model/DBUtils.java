package com.gautam.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtils {
//	public static void main(String[] args) {
//		Connection connection=getConnection();
//		System.out.println(connection!=null);
//	}
	
	public static Connection getConnection() {
		Properties properties=readPropertiesFile();
//		System.out.println(properties);
		Connection connection=null;
		try {
			Class.forName(properties.getProperty("db.classname"));
			connection=DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
			connection.setAutoCommit(false);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Properties readPropertiesFile() {
		FileInputStream fileInputStream=null;
		Properties properties=null;
		try {
			fileInputStream=new FileInputStream("application.properties");
			properties=new Properties();
			properties.load(fileInputStream);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
