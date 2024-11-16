package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Replace with your actual database details
    private static final String URL = "jdbc:mysql://localhost:3306/iss_data";  // Your database is iss_data
    private static final String USER = "root";  // Your MySQL username is root
    private static final String PASSWORD = "1234";  // Replace with your MySQL password ("" if empty)

    public static Connection connect() {
        try {
            // Try to establish a connection to MySQL
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to the MySQL database!");
            return conn;
        } catch (SQLException e) {
            // If the connection fails, print the error
            System.out.println("Failed to connect to the MySQL database.");
            e.printStackTrace();  // This will print the exact error message
            return null;
        }
    }

    public static void main(String[] args) {
        // Test the connection
        Connection conn = connect();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
