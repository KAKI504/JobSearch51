package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.exceptions.VacancyNotFoundException;
import org.example.jobsearch_51.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

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
    public VacancyDto getVacancyById(@PathVariable int id) {
        log.info("Requesting vacancy with id: {}", id);
        VacancyDto vacancy = vacancyService.getVacancyById(id);
        if (vacancy == null) {
            throw new VacancyNotFoundException(id);
        }
        return vacancy;
    }

    @GetMapping("category/{categoryId}")
    public List<VacancyDto> getVacanciesByCategory(@PathVariable int categoryId) {
        log.info("Requesting vacancies for category id: {}", categoryId);
        return vacancyService.getVacanciesByCategory(categoryId);
    }

    @GetMapping("employer/{employerId}")
    public List<VacancyDto> getVacanciesByEmployer(@PathVariable int employerId) {
        log.info("Requesting vacancies for employer id: {}", employerId);
        return vacancyService.getVacanciesByEmployer(employerId);
    }

    @PostMapping
    public HttpStatus createVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        log.info("Creating new vacancy by employer id: {}", vacancyDto.getAuthorId());
        vacancyService.createVacancy(vacancyDto);
        log.info("Vacancy created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        log.info("Updating vacancy with id: {}", vacancyDto.getId());
        vacancyService.updateVacancy(vacancyDto);
        log.info("Vacancy updated successfully");
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteVacancy(@PathVariable int id) {
        log.info("Deleting vacancy with id: {}", id);
        vacancyService.deleteVacancy(id);
        log.info("Vacancy deleted successfully");
        return HttpStatus.OK;
    }

    @PutMapping("{id}/status")
    public HttpStatus toggleVacancyStatus(@PathVariable int id, @RequestParam boolean isActive) {
        log.info("Toggling vacancy status to {} for id: {}", isActive, id);
        vacancyService.toggleVacancyStatus(id, isActive);
        log.info("Vacancy status toggled successfully");
        return HttpStatus.OK;
    }
}