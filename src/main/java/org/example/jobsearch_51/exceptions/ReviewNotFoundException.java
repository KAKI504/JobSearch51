package org.example.jobsearch_51.exceptions;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException() {
        super("Отзыв не найден");
    }

    public ReviewNotFoundException(int id) {
        super("Отзыв с ID " + id + " не найден");
    }
}