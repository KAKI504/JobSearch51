package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.VacancyDao;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.model.Vacancy;
import org.example.jobsearch_51.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;

    @Override
    public List<VacancyDto> getAllVacancies() {
        return vacancyDao.getAllVacancies().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDto> getActiveVacancies() {
        return vacancyDao.getActiveVacancies().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(int categoryId) {
        return vacancyDao.getVacanciesByCategory(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDto> getVacanciesByEmployer(int employerId) {
        return vacancyDao.getVacanciesByEmployer(employerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDto getVacancyById(int id) {
        return vacancyDao.getVacancyById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Vacancy not found with id: " + id));
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = convertToEntity(vacancyDto);
        vacancyDao.createVacancy(vacancy);
    }

    @Override
    public void updateVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = convertToEntity(vacancyDto);
        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void deleteVacancy(int vacancyId) {
        vacancyDao.deleteVacancy(vacancyId);
    }

    @Override
    public void toggleVacancyStatus(int vacancyId, boolean isActive) {
        vacancyDao.updateVacancyStatus(vacancyId, isActive);
    }

    private VacancyDto convertToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.isActive())
                .authorId(vacancy.getAuthorId())
                .build();
    }

    private Vacancy convertToEntity(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDto.getId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setActive(vacancyDto.isActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        return vacancy;
    }
}