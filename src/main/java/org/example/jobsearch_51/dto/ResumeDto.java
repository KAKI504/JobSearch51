package org.example.jobsearch_51.dto;

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
    private int applicantId;
    private String name;
    private int categoryId;
    private String salary;
    private boolean isActive;
}