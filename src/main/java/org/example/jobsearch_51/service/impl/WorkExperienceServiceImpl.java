package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.WorkExperienceDao;
import org.example.jobsearch_51.dto.WorkExperienceDto;
import org.example.jobsearch_51.model.WorkExperience;
import org.example.jobsearch_51.service.WorkExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceDao workExperienceDao;

    @Override
    public List<WorkExperienceDto> getWorkExperiencesByResume(int resumeId) {
        return workExperienceDao.getWorkExperienceByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkExperienceDto getWorkExperienceById(int id) {
        return workExperienceDao.getWorkExperienceById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Work experience not found with id: " + id));
    }

    @Override
    public void createWorkExperience(WorkExperienceDto workExperienceDto) {
        WorkExperience workExperience = convertToEntity(workExperienceDto);
        workExperienceDao.createWorkExperience(workExperience);
    }

    @Override
    public void updateWorkExperience(WorkExperienceDto workExperienceDto) {
        WorkExperience workExperience = convertToEntity(workExperienceDto);
        workExperienceDao.updateWorkExperience(workExperience);
    }

    @Override
    public void deleteWorkExperience(int id) {
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