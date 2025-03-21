package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.WorkExperienceDto;
import java.util.List;

public interface WorkExperienceService {
    List<WorkExperienceDto> getWorkExperiencesByResume(int resumeId);
    WorkExperienceDto getWorkExperienceById(int id);
    void createWorkExperience(WorkExperienceDto workExperienceDto);
    void updateWorkExperience(WorkExperienceDto workExperienceDto);
    void deleteWorkExperience(int id);
}