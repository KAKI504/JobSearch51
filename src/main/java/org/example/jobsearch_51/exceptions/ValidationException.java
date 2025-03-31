package org.example.jobsearch_51.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException() {
        super("Ошибка валидации данных");
    }

    public ValidationException(String message) {
        super(message);
    }
}