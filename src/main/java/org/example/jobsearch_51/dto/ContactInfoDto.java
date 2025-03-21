package org.example.jobsearch_51.dto;

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
    private int typeId;
    private int resumeId;
    private String value;
}