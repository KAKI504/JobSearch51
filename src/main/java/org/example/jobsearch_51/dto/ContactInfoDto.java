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
public class ContactInfoDto {
    private int id;

    @Positive(message = "ID типа контакта должен быть положительным числом")
    private int typeId;

    @Positive(message = "ID резюме должен быть положительным числом")
    private int resumeId;

    @NotBlank(message = "Значение контакта не может быть пустым")
    @Size(max = 255, message = "Значение контакта должно быть не более 255 символов")
    private String value;
}