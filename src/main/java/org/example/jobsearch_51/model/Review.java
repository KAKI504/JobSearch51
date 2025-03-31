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
public class Review {
    private int id;
    private int vacancyId;
    private int userId;
    private String content;
    private int rating;
    private LocalDateTime createdDate;
}