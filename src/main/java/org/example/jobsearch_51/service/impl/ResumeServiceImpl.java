package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.ResumeDao;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dto.ResumeDto;
import org.example.jobsearch_51.exceptions.ResumeNotFoundException;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.model.Resume;
import org.example.jobsearch_51.service.ResumeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserDao userDao;

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
        if (categoryId <= 0) {
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        return resumeDao.getResumesByCategory(categoryId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant(int applicantId) {
        if (applicantId <= 0) {
            throw new IllegalArgumentException("ID соискателя должен быть положительным числом");
        }

        if (!userDao.getUserById(applicantId).isPresent()) {
            throw new UserNotFoundException(applicantId);
        }

        return resumeDao.getResumesByApplicant(applicantId).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public ResumeDto getResumeById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        return resumeDao.getResumeById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResumeNotFoundException(id));
    }

    @Override
    public void createResume(ResumeDto resumeDto) {
        if (resumeDto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        if (resumeDto.getApplicantId() <= 0) {
            throw new IllegalArgumentException("ID соискателя должен быть положительным числом");
        }

        if (!userDao.getUserById(resumeDto.getApplicantId()).isPresent()) {
            throw new UserNotFoundException(resumeDto.getApplicantId());
        }

        Resume resume = convertToEntity(resumeDto);
        resumeDao.createResume(resume);
    }

    @Override
    public void updateResume(ResumeDto resumeDto) {
        if (resumeDto.getId() <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.resumeExists(resumeDto.getId())) {
            throw new ResumeNotFoundException(resumeDto.getId());
        }

        Resume resume = convertToEntity(resumeDto);
        resumeDao.updateResume(resume);
    }

    @Override
    public void deleteResume(int resumeId) {
        if (resumeId <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.resumeExists(resumeId)) {
            throw new ResumeNotFoundException(resumeId);
        }

        resumeDao.deleteResume(resumeId);
    }

    @Override
    public void toggleResumeStatus(int resumeId, boolean isActive) {
        if (resumeId <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.resumeExists(resumeId)) {
            throw new ResumeNotFoundException(resumeId);
        }

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