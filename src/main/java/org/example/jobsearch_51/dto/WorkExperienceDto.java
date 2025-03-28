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
public class WorkExperienceDto {
    private int id;

    @Positive(message = "ID резюме должен быть положительным числом")
    private int resumeId;

    @PositiveOrZero(message = "Стаж работы не может быть отрицательным")
    @Max(value = 70, message = "Стаж работы должен быть не более 70 лет")
    private int years;

    @NotBlank(message = "Название компании не может быть пустым")
    @Size(min = 2, max = 255, message = "Название компании должно быть от 2 до 255 символов")
    private String companyName;

    @NotBlank(message = "Должность не может быть пустой")
    @Size(min = 2, max = 255, message = "Должность должна быть от 2 до 255 символов")
    private String position;

    @Size(max = 1000, message = "Обязанности должны быть не более 1000 символов")
    private String responsibilities;
}