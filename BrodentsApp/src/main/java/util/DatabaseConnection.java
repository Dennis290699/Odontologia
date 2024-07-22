package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final Properties props = new Properties();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find application.properties");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }

        URL = props.getProperty("db.url");
        USER = props.getProperty("db.user");
        PASSWORD = props.getProperty("db.password");
    }

    private DatabaseConnection() {
        // Prevent instantiation
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found.");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Remove the closeConnection method, as it's now managed at the connection level
}
