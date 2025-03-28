package org.example.jobsearch_51.error;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponseBody {
    private String error;
    private Map<String, Object> reasons;
}