package org.example.jobsearch_51.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private int id;
    private int resumeId;
    private int vacancyId;
    private boolean confirmation;
}