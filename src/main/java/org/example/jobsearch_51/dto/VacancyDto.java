package org.example.jobsearch_51.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.jobsearch_51.validation.ValidExperienceRange;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private Integer id;

    @NotBlank(message = "Название вакансии не может быть пустым")
    @Size(min = 3, max = 255, message = "Название вакансии должно быть от 3 до 255 символов")
    private String title;

    @NotBlank(message = "Описание вакансии не может быть пустым")
    @Size(min = 10, message = "Описание вакансии должно содержать минимум 10 символов")
    private String description;

    @Positive(message = "ID категории должен быть положительным числом")
    private Integer categoryId;

    @NotBlank(message = "Зарплата не может быть пустой")
    @Pattern(regexp = "^[0-9]+(\\-[0-9]+)?$", message = "Формат зарплаты должен быть числом или диапазоном (например: 50000 или 40000-60000)")
    private String salary;

    @PositiveOrZero(message = "Минимальный опыт работы не может быть отрицательным")
    private Integer expFrom;

    @PositiveOrZero(message = "Максимальный опыт работы не может быть отрицательным")
    private Integer expTo;

    private boolean isActive;

    private Integer authorId;

    private String requirements;
    private String location;
}