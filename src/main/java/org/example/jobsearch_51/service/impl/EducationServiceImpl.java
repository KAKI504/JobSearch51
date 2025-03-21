package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.EducationDao;
import org.example.jobsearch_51.dto.EducationDto;
import org.example.jobsearch_51.model.Education;
import org.example.jobsearch_51.service.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final EducationDao educationDao;

    @Override
    public List<EducationDto> getEducationsByResume(int resumeId) {
        return educationDao.getEducationByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EducationDto getEducationById(int id) {
        return educationDao.getEducationById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
    }

    @Override
    public void createEducation(EducationDto educationDto) {
        Education education = convertToEntity(educationDto);
        educationDao.createEducation(education);
    }

    @Override
    public void updateEducation(EducationDto educationDto) {
        Education education = convertToEntity(educationDto);
        educationDao.updateEducation(education);
    }

    @Override
    public void deleteEducation(int id) {
        educationDao.deleteEducation(id);
    }

    private EducationDto convertToDto(Education education) {
        return EducationDto.builder()
                .id(education.getId())
                .resumeId(education.getResumeId())
                .institution(education.getInstitution())
                .program(education.getProgram())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .degree(education.getDegree())
                .build();
    }

    private Education convertToEntity(EducationDto educationDto) {
        Education education = new Education();
        education.setId(educationDto.getId());
        education.setResumeId(educationDto.getResumeId());
        education.setInstitution(educationDto.getInstitution());
        education.setProgram(educationDto.getProgram());
        education.setStartDate(educationDto.getStartDate());
        education.setEndDate(educationDto.getEndDate());
        education.setDegree(educationDto.getDegree());
        return education;
    }
}