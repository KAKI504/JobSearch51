package org.example.jobsearch_51.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(Exception e) {
        String errorMsg = e.getMessage();
        log.error("Error occurred: {}", errorMsg);
        return ErrorResponseBody.builder()
                .error(errorMsg)
                .reasons(Map.of("errors", List.of(errorMsg)))
                .build();
    }

    @Override
    public ErrorResponseBody makeResponse(BindingResult bindingResult) {
        Map<String, List<String>> reasons = new HashMap<>();

        bindingResult.getFieldErrors().stream()
                .filter(e -> e.getDefaultMessage() != null)
                .forEach(e -> {
                    if (!reasons.containsKey(e.getField())) {
                        reasons.put(e.getField(), new ArrayList<>());
                    }
                    reasons.get(e.getField()).add(e.getDefaultMessage());
                    log.debug("Validation error - Field: {}, Message: {}", e.getField(), e.getDefaultMessage());
                });

        return ErrorResponseBody.builder()
                .error("Ошибка валидации")
                .reasons(Map.of("errors", reasons))
                .build();
    }
}