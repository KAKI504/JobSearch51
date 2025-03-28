package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.ResumeDao;
import org.example.jobsearch_51.dao.WorkExperienceDao;
import org.example.jobsearch_51.dto.WorkExperienceDto;
import org.example.jobsearch_51.exceptions.ResumeNotFoundException;
import org.example.jobsearch_51.exceptions.WorkExperienceNotFoundException;
import org.example.jobsearch_51.model.WorkExperience;
import org.example.jobsearch_51.service.WorkExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceDao workExperienceDao;
    private final ResumeDao resumeDao;

    @Override
    public List<WorkExperienceDto> getWorkExperiencesByResume(int resumeId) {
        if (resumeId <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.getResumeById(resumeId).isPresent()) {
            throw new ResumeNotFoundException(resumeId);
        }

        return workExperienceDao.getWorkExperienceByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkExperienceDto getWorkExperienceById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID опыта работы должен быть положительным числом");
        }

        return workExperienceDao.getWorkExperienceById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new WorkExperienceNotFoundException(id));
    }

    @Override
    public void createWorkExperience(WorkExperienceDto workExperienceDto) {
        if (workExperienceDto.getResumeId() <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.getResumeById(workExperienceDto.getResumeId()).isPresent()) {
            throw new ResumeNotFoundException(workExperienceDto.getResumeId());
        }

        if (workExperienceDto.getYears() < 0) {
            throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
        }

        WorkExperience workExperience = convertToEntity(workExperienceDto);
        workExperienceDao.createWorkExperience(workExperience);
    }

    @Override
    public void updateWorkExperience(WorkExperienceDto workExperienceDto) {
        if (workExperienceDto.getId() <= 0) {
            throw new IllegalArgumentException("ID опыта работы должен быть положительным числом");
        }

        if (!workExperienceDao.workExperienceExists(workExperienceDto.getId())) {
            throw new WorkExperienceNotFoundException(workExperienceDto.getId());
        }

        if (workExperienceDto.getYears() < 0) {
            throw new IllegalArgumentException("Опыт работы не может быть отрицательным");
        }

        WorkExperience workExperience = convertToEntity(workExperienceDto);
        workExperienceDao.updateWorkExperience(workExperience);
    }

    @Override
    public void deleteWorkExperience(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID опыта работы должен быть положительным числом");
        }

        if (!workExperienceDao.workExperienceExists(id)) {
            throw new WorkExperienceNotFoundException(id);
        }

        workExperienceDao.deleteWorkExperience(id);
    }

    private WorkExperienceDto convertToDto(WorkExperience workExperience) {
        return WorkExperienceDto.builder()
                .id(workExperience.getId())
                .resumeId(workExperience.getResumeId())
                .years(workExperience.getYears())
                .companyName(workExperience.getCompanyName())
                .position(workExperience.getPosition())
                .responsibilities(workExperience.getResponsibilities())
                .build();
    }

    private WorkExperience convertToEntity(WorkExperienceDto workExperienceDto) {
        WorkExperience workExperience = new WorkExperience();
        workExperience.setId(workExperienceDto.getId());
        workExperience.setResumeId(workExperienceDto.getResumeId());
        workExperience.setYears(workExperienceDto.getYears());
        workExperience.setCompanyName(workExperienceDto.getCompanyName());
        workExperience.setPosition(workExperienceDto.getPosition());
        workExperience.setResponsibilities(workExperienceDto.getResponsibilities());
        return workExperience;
    }
}