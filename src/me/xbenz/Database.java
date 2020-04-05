package me.xbenz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	
	private static Connection connection;
	private static PreparedStatement statement;
	private static String host;
	private static String database;
	private static String username;
	private static String password;
  
	public static void openConnection() {
	    host = "";
	    database = "";
	    username = "";
	    password = "";
	    try {
	    	if ((connection != null) && (!connection.isClosed())) {
	    		return;
	    	}
	    	Class.forName("com.mysql.jdbc.Driver");
	    	connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true", username, password);
	    	System.out.println("[!] Database has been connected successfully.");
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	}
  
	public static void closeConnection() {
		try {
			if ((connection == null) && (connection.isClosed())) {
				return;
			}	
			connection.close();
			System.out.println("[!] Database has been disconnected successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
  
  public static Connection getConnection() {
    return connection;
  }
  
  public static PreparedStatement getStatement() {
    return statement;
  }
  
}
