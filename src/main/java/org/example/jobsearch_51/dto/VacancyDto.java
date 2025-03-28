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
@ValidExperienceRange
public class VacancyDto {
    private int id;

    @NotBlank(message = "Название вакансии не может быть пустым")
    @Size(min = 3, max = 255, message = "Название вакансии должно быть от 3 до 255 символов")
    private String name;

    @NotBlank(message = "Описание вакансии не может быть пустым")
    @Size(min = 10, message = "Описание вакансии должно содержать минимум 10 символов")
    private String description;

    @Positive(message = "ID категории должен быть положительным числом")
    private int categoryId;

    @NotBlank(message = "Зарплата не может быть пустой")
    @Pattern(regexp = "^[0-9]+(\\-[0-9]+)?$", message = "Формат зарплаты должен быть числом или диапазоном (например: 50000 или 40000-60000)")
    private String salary;

    @PositiveOrZero(message = "Минимальный опыт работы не может быть отрицательным")
    private int expFrom;

    @PositiveOrZero(message = "Максимальный опыт работы не может быть отрицательным")
    private int expTo;

    private boolean isActive;

    @Positive(message = "ID автора должен быть положительным числом")
    private int authorId;
}