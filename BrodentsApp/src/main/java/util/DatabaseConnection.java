package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:postgresql://localhost:5433/NewBrodents";
	private static final String USER = "newadmin";
	private static final String PASSWORD = "newadmin123";
	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Connection to the database established successfully.");
			}
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database.");
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Connection closed successfully.");
			} catch (SQLException e) {
				System.out.println("Failed to close the connection.");
				e.printStackTrace();
			}
		}
	}
}