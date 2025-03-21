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
public class Vacancy {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private String salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private int authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}