package org.example.jobsearch_51.exceptions;

public class ResponseNotFoundException extends RuntimeException {
    public ResponseNotFoundException() {
        super("Отклик не найден");
    }

    public ResponseNotFoundException(String message) {
        super(message);
    }

    public ResponseNotFoundException(int id) {
        super("Отклик с ID " + id + " не найден");
    }
}