package org.example.jobsearch_51.error.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.error.ErrorResponseBody;
import org.example.jobsearch_51.error.ErrorService;
import org.example.jobsearch_51.exceptions.UserAlreadyExistsException;
import org.example.jobsearch_51.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    private final ErrorService errorService;

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseBody> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("User already exists: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseBody> handleValidationException(ValidationException e) {
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorService.makeResponse(e));
    }
}