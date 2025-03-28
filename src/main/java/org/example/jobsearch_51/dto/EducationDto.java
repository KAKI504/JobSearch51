package org.example.jobsearch_51.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.jobsearch_51.validation.ValidDateRange;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidDateRange
public class EducationDto {
    private int id;

    @Positive(message = "ID резюме должен быть положительным числом")
    private int resumeId;

    @NotBlank(message = "Название учебного заведения не может быть пустым")
    @Size(min = 2, max = 255, message = "Название учебного заведения должно быть от 2 до 255 символов")
    private String institution;

    @Size(max = 255, message = "Название программы обучения должно быть не более 255 символов")
    private String program;

    @NotNull(message = "Дата начала обучения не может быть пустой")
    @PastOrPresent(message = "Дата начала обучения должна быть в прошлом или настоящем")
    private LocalDate startDate;

    @NotNull(message = "Дата окончания обучения не может быть пустой")
    private LocalDate endDate;

    @Size(max = 255, message = "Степень должна быть не более 255 символов")
    private String degree;
}