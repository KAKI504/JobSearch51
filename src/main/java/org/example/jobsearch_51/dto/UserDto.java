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
public class UserDto {
    private Integer id;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
    private String name;

    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String surname;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    @Size(max = 100, message = "Email должен быть не более 100 символов")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 100, message = "Пароль должен быть от 6 до 100 символов")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$",
            message = "Пароль должен содержать как минимум одну цифру и одну букву")
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{10,20}$", message = "Некорректный формат номера телефона")
    private String phoneNumber;

    @NotBlank(message = "Тип аккаунта не может быть пустым")
    @Pattern(regexp = "^(APPLICANT|EMPLOYER|ADMIN)$", message = "Тип аккаунта должен быть 'APPLICANT', 'EMPLOYER' или 'ADMIN'")
    private String accountType;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Max(value = 120, message = "Возраст должен быть не более 120 лет")
    private int age;

    private boolean enabled = true;
}