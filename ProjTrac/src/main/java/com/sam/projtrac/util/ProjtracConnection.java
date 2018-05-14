package com.sam.projtrac.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ProjtracConnection {
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName("org.h2.Driver");
		
        conn = DriverManager.getConnection("jdbc:h2:file:./ptrac","sa","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return conn;
	}
	
	
	 public static DriverManagerDataSource getDataSource() {
		   DriverManagerDataSource dataSource = new DriverManagerDataSource();
		   dataSource.setDriverClassName("org.h2.Driver");
		   dataSource.setUrl("jdbc:h2:file:./ptrac");
		   dataSource.setUsername("sa");
		   dataSource.setPassword("");
		   return dataSource;
		     }

}
