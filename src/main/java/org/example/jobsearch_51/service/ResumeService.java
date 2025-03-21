package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.ResumeDto;
import java.util.List;

public interface ResumeService {
    List<ResumeDto> getAllResumes();
    List<ResumeDto> getActiveResumes();
    List<ResumeDto> getResumesByCategory(int categoryId);
    List<ResumeDto> getResumesByApplicant(int applicantId);
    ResumeDto getResumeById(int id);
    void createResume(ResumeDto resumeDto);
    void updateResume(ResumeDto resumeDto);
    void deleteResume(int resumeId);
    void toggleResumeStatus(int resumeId, boolean isActive);
}