package org.example.jobsearch_51.exceptions;

public class WorkExperienceNotFoundException extends RuntimeException {
    public WorkExperienceNotFoundException() {
        super("Work experience not found");
    }

    public WorkExperienceNotFoundException(String message) {
        super(message);
    }

    public WorkExperienceNotFoundException(int id) {
        super("Work experience not found with id: " + id);
    }
}