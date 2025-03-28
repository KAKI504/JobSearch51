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
public class MessageDto {
    private int id;

    @Positive(message = "ID отклика должен быть положительным числом")
    private int responseId;

    @NotBlank(message = "Содержание сообщения не может быть пустым")
    @Size(max = 1000, message = "Содержание сообщения должно быть не более 1000 символов")
    private String content;

    private LocalDateTime timestamp;

    @Positive(message = "ID отправителя должен быть положительным числом")
    private int senderId;
}