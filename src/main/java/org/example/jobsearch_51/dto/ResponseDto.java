package org.example.jobsearch_51.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int id;

    @Positive(message = "ID резюме должен быть положительным числом")
    private int resumeId;

    @Positive(message = "ID вакансии должен быть положительным числом")
    private int vacancyId;

    private boolean confirmation;
}