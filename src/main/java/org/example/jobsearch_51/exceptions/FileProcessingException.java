package org.example.jobsearch_51.exceptions;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException() {
        super("Ошибка обработки файла");
    }

    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}