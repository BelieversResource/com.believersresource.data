package com.believersresource.data;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	private static ComboPooledDataSource dataSource;
	
	public static Connection getConnection() {
		if (dataSource==null) init();
		try{
			cleanUp();
			return dataSource.getConnection();
		} catch(Exception ex) {
			System.out.println(ex);
			return null; 
		}
	}
	
	
	private static void cleanUp()
	{
		try{
			if (dataSource.getNumConnectionsAllUsers()>15) dataSource.hardReset();
		} catch (Exception ex) {}
	}
	
	public static void init()
	{
		try {
			dataSource = new ComboPooledDataSource("com.mysql.jdbc.Driver");
		}  catch (Exception e) {
			e.printStackTrace();
		}
				

		dataSource.setJdbcUrl("jdbc:mysql://localhost/believersresource");
		
		dataSource.setUser("YourUserName");
		dataSource.setPassword("YourPassword");
		dataSource.setMinPoolSize(5);
		dataSource.setAcquireIncrement(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setCheckoutTimeout(60);
	}
	
	
}
