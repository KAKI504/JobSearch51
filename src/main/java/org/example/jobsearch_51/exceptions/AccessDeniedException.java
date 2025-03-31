package org.example.jobsearch_51.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Доступ запрещен");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}