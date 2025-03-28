package org.example.jobsearch_51.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Пользователь уже существует");
    }

    public UserAlreadyExistsException(String email) {
        super("Пользователь с email " + email + " уже существует");
    }
}