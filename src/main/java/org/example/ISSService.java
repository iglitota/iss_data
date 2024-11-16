package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.google.gson.Gson;

public class ISSService {
    public static void main(String[] args) {
        // Step 1: Create the HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Step 2: Make a request to the ISS API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://api.open-notify.org/iss-now.json"))
                .build();

        // Step 3: Send the request and get the response
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body) // Get the response body (JSON string)
                .thenApply(response -> {
                    // Step 4: Parse the JSON response using Gson
                    Gson gson = new Gson();
                    ISSResponse issResponse = gson.fromJson(response, ISSResponse.class);

                    // Step 5: Save the data to the database
                    saveDataToDatabase(issResponse);

                    // Step 6: Print the data
                    System.out.println("Message: " + issResponse.getMessage());
                    System.out.println("Timestamp: " + issResponse.getTimestamp());
                    System.out.println("ISS Position:");
                    System.out.println("  Latitude: " + issResponse.getIss_position().getLatitude());
                    System.out.println("  Longitude: " + issResponse.getIss_position().getLongitude());
                    return null;
                })
                .join(); // Wait for the response to finish
    }

    private static void saveDataToDatabase(ISSResponse issResponse) {
        try (Connection conn = DatabaseConnection.connect()) {
            if (conn != null) {
                // Prepare SQL statement to insert data into the database
                String query = "INSERT INTO iss_data (message, timestamp, latitude, longitude) VALUES (?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    // Set the values to insert into the database
                    stmt.setString(1, issResponse.getMessage());  // Message from API response
                    stmt.setLong(2, issResponse.getTimestamp());  // Timestamp from API response
                    stmt.setString(3, issResponse.getIss_position().getLatitude());  // Latitude
                    stmt.setString(4, issResponse.getIss_position().getLongitude()); // Longitude

                    // Execute the insert query
                    stmt.executeUpdate();
                    System.out.println("Data saved to the database.");
                }
            }
        } catch (SQLException e) {
            // If there is an error while saving to the database, print error
            System.out.println("Error saving data to the database.");
            e.printStackTrace();
        }
    }
}
