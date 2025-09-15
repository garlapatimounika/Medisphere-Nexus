package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
private static Connection con;
	
	static{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/016_25Batch";
			con=DriverManager.getConnection(url,"root","Admin");
			
		} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			
		  catch (SQLException e)  {
			
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection(){
		return con;
	}


}
