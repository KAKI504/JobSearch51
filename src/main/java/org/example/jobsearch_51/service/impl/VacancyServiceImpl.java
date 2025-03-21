package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    @Override
    public List<VacancyDto> getAllVacancies() {
        return new ArrayList<>();
    }

    @Override
    public List<VacancyDto> getActiveVacancies() {
        return new ArrayList<>();
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(int categoryId) {
        return new ArrayList<>();
    }

    @Override
    public List<VacancyDto> getVacanciesByEmployer(int employerId) {
        return new ArrayList<>();
    }

    @Override
    public VacancyDto getVacancyById(int id) {
        return new VacancyDto();
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
    }

    @Override
    public void updateVacancy(VacancyDto vacancyDto) {
    }

    @Override
    public void deleteVacancy(int vacancyId) {
    }

    @Override
    public void toggleVacancyStatus(int vacancyId, boolean isActive) {
    }
}