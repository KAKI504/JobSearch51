package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dao.VacancyDao;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.exceptions.VacancyNotFoundException;
import org.example.jobsearch_51.model.Vacancy;
import org.example.jobsearch_51.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    private final VacancyDao vacancyDao;
    private final UserDao userDao;

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
        if (categoryId <= 0) {
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        return vacancyDao.getVacanciesByCategory(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDto> getVacanciesByEmployer(int employerId) {
        if (employerId <= 0) {
            throw new IllegalArgumentException("ID работодателя должен быть положительным числом");
        }

        if (!userDao.getUserById(employerId).isPresent()) {
            throw new UserNotFoundException(employerId);
        }

        return vacancyDao.getVacanciesByEmployer(employerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDto getVacancyById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        return vacancyDao.getVacancyById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new VacancyNotFoundException(id));
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        if (vacancyDto.getCategoryId() <= 0) {
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        if (vacancyDto.getAuthorId() <= 0) {
            throw new IllegalArgumentException("ID автора должен быть положительным числом");
        }

        if (!userDao.getUserById(vacancyDto.getAuthorId()).isPresent()) {
            throw new UserNotFoundException(vacancyDto.getAuthorId());
        }

        Vacancy vacancy = convertToEntity(vacancyDto);
        vacancyDao.createVacancy(vacancy);
    }

    @Override
    public void updateVacancy(VacancyDto vacancyDto) {
        if (vacancyDto.getId() <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        if (!vacancyDao.getVacancyById(vacancyDto.getId()).isPresent()) {
            throw new VacancyNotFoundException(vacancyDto.getId());
        }


        Vacancy vacancy = convertToEntity(vacancyDto);
        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void deleteVacancy(int vacancyId) {
        if (vacancyId <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        if (!vacancyDao.getVacancyById(vacancyId).isPresent()) {
            throw new VacancyNotFoundException(vacancyId);
        }

        vacancyDao.deleteVacancy(vacancyId);
    }

    @Override
    public void toggleVacancyStatus(int vacancyId, boolean isActive) {
        if (vacancyId <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        if (!vacancyDao.getVacancyById(vacancyId).isPresent()) {
            throw new VacancyNotFoundException(vacancyId);
        }

        vacancyDao.updateVacancyStatus(vacancyId, isActive);
    }

    private VacancyDto convertToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .title(vacancy.getName())  // изменено с name на title
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
        vacancy.setName(vacancyDto.getTitle());  // изменено с getName() на getTitle()
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