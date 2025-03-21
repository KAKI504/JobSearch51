package org.example.jobsearch_51.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    private int id;
    private String name;
    private String description;
    private int categoryId;
    private String salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private int authorId;
}