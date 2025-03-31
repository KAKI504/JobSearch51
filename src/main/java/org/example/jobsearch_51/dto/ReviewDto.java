package org.example.jobsearch_51.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private int id;

    @Positive(message = "ID вакансии должен быть положительным числом")
    private int vacancyId;

    private int userId;

    @NotBlank(message = "Содержание отзыва не может быть пустым")
    @Size(min = 10, max = 1000, message = "Содержание отзыва должно быть от 10 до 1000 символов")
    private String content;

    @Min(value = 1, message = "Рейтинг должен быть не менее 1")
    @Max(value = 5, message = "Рейтинг должен быть не более 5")
    private int rating;

    private LocalDateTime createdDate;
}