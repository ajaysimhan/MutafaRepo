package etime.daoUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://localhost/ResourceAllocation";
	private final String USERNAME = "root";
	private final String PASSWORD = "admin";
	private static MySQLConnection mySql = new MySQLConnection();
	private Connection connection;
	
	private MySQLConnection() {}
	
	public static MySQLConnection getMySQLConnection() {
		return mySql;
	}
	
	public Connection connect() throws SQLException {
		connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		return connection;
	}
	public void close() throws SQLException {
		if(connection != null && !(connection.isClosed())) {
			connection.close();
		}
	}
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
