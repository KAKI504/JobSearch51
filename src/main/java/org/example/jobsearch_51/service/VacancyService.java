package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.VacancyDto;
import java.util.List;

public interface VacancyService {
    List<VacancyDto> getAllVacancies();
    List<VacancyDto> getActiveVacancies();
    List<VacancyDto> getVacanciesByCategory(int categoryId);
    List<VacancyDto> getVacanciesByEmployer(int employerId);
    VacancyDto getVacancyById(int id);
    void createVacancy(VacancyDto vacancyDto);
    void updateVacancy(VacancyDto vacancyDto);
    void deleteVacancy(int vacancyId);
    void toggleVacancyStatus(int vacancyId, boolean isActive);
}