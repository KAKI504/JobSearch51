package org.example.jobsearch_51.error;

import org.springframework.validation.BindingResult;

public interface ErrorService {
    ErrorResponseBody makeResponse(Exception e);
    ErrorResponseBody makeResponse(BindingResult bindingResult);
}