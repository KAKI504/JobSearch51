package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.exceptions.AccessDeniedException;
import org.example.jobsearch_51.exceptions.VacancyNotFoundException;
import org.example.jobsearch_51.service.VacancyService;
import org.example.jobsearch_51.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
@Validated
public class VacancyController {
    private final VacancyService vacancyService;
    private final SecurityUtil securityUtil;

    @GetMapping
    public List<VacancyDto> getAllVacancies() {
        log.info("Requesting all vacancies");
        return vacancyService.getAllVacancies();
    }

    @GetMapping("active")
    public List<VacancyDto> getActiveVacancies() {
        log.info("Requesting all active vacancies");
        return vacancyService.getActiveVacancies();
    }

    @GetMapping("{id}")
    public VacancyDto getVacancyById(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        log.info("Requesting vacancy with id: {}", id);
        VacancyDto vacancy = vacancyService.getVacancyById(id);
        if (vacancy == null) {
            throw new VacancyNotFoundException(id);
        }
        return vacancy;
    }

    @GetMapping("category/{categoryId}")
    public List<VacancyDto> getVacanciesByCategory(
            @PathVariable @Min(value = 1, message = "ID категории должен быть положительным числом") int categoryId) {
        log.info("Requesting vacancies for category id: {}", categoryId);
        return vacancyService.getVacanciesByCategory(categoryId);
    }

    @GetMapping("employer/{employerId}")
    public List<VacancyDto> getVacanciesByEmployer(
            @PathVariable @Min(value = 1, message = "ID работодателя должен быть положительным числом") int employerId) {
        log.info("Requesting vacancies for employer id: {}", employerId);
        return vacancyService.getVacanciesByEmployer(employerId);
    }

    @PostMapping
    public HttpStatus createVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        return createVacancyInternal(vacancyDto);
    }

    @PostMapping("create")
    public HttpStatus createVacancyWithCreate(@Valid @RequestBody VacancyDto vacancyDto) {
        UserDto currentUser = securityUtil.getCurrentUser();
        if (vacancyDto.getAuthorId() == null) {
            vacancyDto.setAuthorId(currentUser.getId());
        }

        return createVacancyInternal(vacancyDto);
    }

    private HttpStatus createVacancyInternal(VacancyDto vacancyDto) {
        UserDto currentUser = securityUtil.getCurrentUser();

        if (!"EMPLOYER".equals(currentUser.getAccountType()) && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to create vacancy by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Только работодатели могут создавать вакансии");
        }

        log.info("Creating new vacancy by employer id: {}", currentUser.getId());

        vacancyDto.setAuthorId(currentUser.getId());

        vacancyService.createVacancy(vacancyDto);
        log.info("Vacancy created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        UserDto currentUser = securityUtil.getCurrentUser();
        VacancyDto existingVacancy = vacancyService.getVacancyById(vacancyDto.getId());

        if (existingVacancy.getAuthorId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to update vacancy by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно редактировать только собственные вакансии");
        }

        log.info("Updating vacancy with id: {}", vacancyDto.getId());
        vacancyService.updateVacancy(vacancyDto);
        log.info("Vacancy updated successfully");
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteVacancy(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        UserDto currentUser = securityUtil.getCurrentUser();
        VacancyDto existingVacancy = vacancyService.getVacancyById(id);

        if (existingVacancy.getAuthorId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to delete vacancy by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно удалять только собственные вакансии");
        }

        log.info("Deleting vacancy with id: {}", id);
        vacancyService.deleteVacancy(id);
        log.info("Vacancy deleted successfully");
        return HttpStatus.OK;
    }

    @PutMapping("{id}/status")
    public HttpStatus toggleVacancyStatus(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id,
            @RequestParam boolean isActive) {
        UserDto currentUser = securityUtil.getCurrentUser();
        VacancyDto existingVacancy = vacancyService.getVacancyById(id);

        if (existingVacancy.getAuthorId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to toggle vacancy status by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно изменять статус только собственных вакансий");
        }

        log.info("Toggling vacancy status to {} for id: {}", isActive, id);
        vacancyService.toggleVacancyStatus(id, isActive);
        log.info("Vacancy status toggled successfully");
        return HttpStatus.OK;
    }
}