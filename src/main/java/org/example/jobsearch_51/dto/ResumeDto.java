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
public class ResumeDto {
    private int id;

    @Positive(message = "ID соискателя должен быть положительным числом")
    private int applicantId;

    @NotBlank(message = "Название резюме не может быть пустым")
    @Size(min = 3, max = 255, message = "Название резюме должно быть от 3 до 255 символов")
    private String name;

    @Positive(message = "ID категории должен быть положительным числом")
    private int categoryId;

    @NotBlank(message = "Желаемая зарплата не может быть пустой")
    @Pattern(regexp = "^[0-9]+(\\-[0-9]+)?$", message = "Формат зарплаты должен быть числом или диапазоном (например: 50000 или 40000-60000)")
    private String salary;

    private boolean isActive;
}