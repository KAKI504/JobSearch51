package org.example.jobsearch_51.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(int id) {
        super("User not found with id: " + id);
    }
}