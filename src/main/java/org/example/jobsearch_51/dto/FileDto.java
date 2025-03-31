package org.example.jobsearch_51.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private MultipartFile file;
    private Long entityId;
}