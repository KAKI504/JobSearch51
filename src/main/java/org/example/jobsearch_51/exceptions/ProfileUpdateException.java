package org.example.jobsearch_51.exceptions;

public class ProfileUpdateException extends RuntimeException {
    public ProfileUpdateException() {
        super("Failed to update user profile");
    }

    public ProfileUpdateException(String message) {
        super(message);
    }

    public ProfileUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}