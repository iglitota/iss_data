package org.example;

public class ISSResponse {
    private String message;
    private long timestamp;  // Field to hold timestamp
    private ISSPosition iss_position;  // Assuming this is another class

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Getter for timestamp
    public long getTimestamp() {
        return timestamp;
    }

    // Getter for iss_position
    public ISSPosition getIss_position() {
        return iss_position;
    }
}
