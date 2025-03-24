package org.example.jobsearch_51.exceptions;

public class EducationNotFoundException extends RuntimeException {
    public EducationNotFoundException() {
        super("Education not found");
    }

    public EducationNotFoundException(String message) {
        super(message);
    }

    public EducationNotFoundException(int id) {
        super("Education not found with id: " + id);
    }
}