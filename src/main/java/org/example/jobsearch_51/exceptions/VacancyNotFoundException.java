package org.example.jobsearch_51.exceptions;

public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException() {
        super("Вакансия не найдена");
    }

    public VacancyNotFoundException(String message) {
        super(message);
    }

    public VacancyNotFoundException(int id) {
        super("Вакансия с ID " + id + " не найдена");
    }
}