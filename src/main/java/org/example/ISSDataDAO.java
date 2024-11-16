package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ISSDataDAO {

    private static final String INSERT_SQL = "INSERT INTO iss_data (message, timestamp, latitude, longitude) VALUES (?, ?, ?, ?)";

    // Method to insert ISS data into the database
    public void insertISSData(ISSData issData) {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

            // Set parameters for the prepared statement
            stmt.setString(1, issData.getMessage());
            stmt.setLong(2, issData.getTimestamp());
            stmt.setString(3, issData.getLatitude());
            stmt.setString(4, issData.getLongitude());

            // Execute the insert statement
            stmt.executeUpdate();
            System.out.println("ISS data inserted successfully!");

        } catch (SQLException e) {
            System.out.println("Error inserting data into database.");
            e.printStackTrace();
        }
    }
}
