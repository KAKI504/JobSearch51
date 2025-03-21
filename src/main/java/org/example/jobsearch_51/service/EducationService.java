package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.EducationDto;
import java.util.List;

public interface EducationService {
    List<EducationDto> getEducationsByResume(int resumeId);
    EducationDto getEducationById(int id);
    void createEducation(EducationDto educationDto);
    void updateEducation(EducationDto educationDto);
    void deleteEducation(int id);
}