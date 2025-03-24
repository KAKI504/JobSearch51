package org.example.jobsearch_51.exceptions;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException() {
        super("Resume not found");
    }

    public ResumeNotFoundException(String message) {
        super(message);
    }

    public ResumeNotFoundException(int id) {
        super("Resume not found with id: " + id);
    }
}