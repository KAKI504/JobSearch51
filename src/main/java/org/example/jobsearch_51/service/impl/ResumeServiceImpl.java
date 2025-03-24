package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.ResumeDao;
import org.example.jobsearch_51.dto.ResumeDto;
import org.example.jobsearch_51.model.Resume;
import org.example.jobsearch_51.service.ResumeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeDto> getAllResumes() {
        return resumeDao.getAllResumes().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getActiveResumes() {
        return resumeDao.getActiveResumes().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByCategory(int categoryId) {
        return resumeDao.getResumesByCategory(categoryId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant(int applicantId) {
        return resumeDao.getResumesByApplicant(applicantId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ResumeDto getResumeById(int id) {
        return resumeDao.getResumeById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
    }

    @Override
    public void createResume(ResumeDto resumeDto) {
        Resume resume = convertToEntity(resumeDto);
        resumeDao.createResume(resume);
    }

    @Override
    public void updateResume(ResumeDto resumeDto) {
        Resume resume = convertToEntity(resumeDto);
        resumeDao.updateResume(resume);
    }

    @Override
    public void deleteResume(int resumeId) {
        resumeDao.deleteResume(resumeId);
    }

    @Override
    public void toggleResumeStatus(int resumeId, boolean isActive) {
        resumeDao.updateResumeStatus(resumeId, isActive);
    }

    private ResumeDto convertToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.isActive())
                .build();
    }

    private Resume convertToEntity(ResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setId(resumeDto.getId());
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setActive(resumeDto.isActive());
        return resume;
    }
}