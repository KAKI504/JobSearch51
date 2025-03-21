package org.example.jobsearch_51.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int id;
    private int responseId;
    private String content;
    private LocalDateTime timestamp;
    private int senderId;
}