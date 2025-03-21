package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.ResumeDto;
import org.example.jobsearch_51.service.ResumeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    @Override
    public List<ResumeDto> getAllResumes() {
        return new ArrayList<>();
    }

    @Override
    public List<ResumeDto> getActiveResumes() {
        return new ArrayList<>();
    }

    @Override
    public List<ResumeDto> getResumesByCategory(int categoryId) {
        return new ArrayList<>();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant(int applicantId) {
        return new ArrayList<>();
    }

    @Override
    public ResumeDto getResumeById(int id) {
        return new ResumeDto();
    }

    @Override
    public void createResume(ResumeDto resumeDto) {
    }

    @Override
    public void updateResume(ResumeDto resumeDto) {
    }

    @Override
    public void deleteResume(int resumeId) {
    }

    @Override
    public void toggleResumeStatus(int resumeId, boolean isActive) {
    }
}