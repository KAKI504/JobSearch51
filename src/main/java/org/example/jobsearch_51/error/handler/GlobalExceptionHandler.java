package org.example.jobsearch_51.error.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.error.ErrorResponseBody;
import org.example.jobsearch_51.error.ErrorService;
import org.example.jobsearch_51.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseBody> handleNoSuchElementException(NoSuchElementException e) {
        log.error("Entity not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler({
            ResumeNotFoundException.class,
            EducationNotFoundException.class,
            WorkExperienceNotFoundException.class,
            UserNotFoundException.class,
            VacancyNotFoundException.class,
            ResponseNotFoundException.class
    })
    public ResponseEntity<ErrorResponseBody> handleEntityNotFoundException(Exception e) {
        log.error("Entity not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseBody> handleValidationExceptions(BindException e) {
        log.error("Validation error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorService.makeResponse(e.getBindingResult()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseBody> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("User already exists: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseBody> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("File size exceeded: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(errorService.makeResponse(new Exception("Размер файла превышает допустимый предел")));
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponseBody> handleFileProcessingException(FileProcessingException e) {
        log.error("File processing error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<ErrorResponseBody> handleProfileUpdateException(ProfileUpdateException e) {
        log.error("Profile update error: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorService.makeResponse(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorService.makeResponse(e));
    }
}