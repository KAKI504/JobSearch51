package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.VacancyDto;
import org.example.jobsearch_51.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @GetMapping("active")
    public List<VacancyDto> getActiveVacancies() {
        return vacancyService.getActiveVacancies();
    }

    @GetMapping("{id}")
    public VacancyDto getVacancyById(@PathVariable int id) {
        return vacancyService.getVacancyById(id);
    }

    @GetMapping("category/{categoryId}")
    public List<VacancyDto> getVacanciesByCategory(@PathVariable int categoryId) {
        return vacancyService.getVacanciesByCategory(categoryId);
    }

    @GetMapping("employer/{employerId}")
    public List<VacancyDto> getVacanciesByEmployer(@PathVariable int employerId) {
        return vacancyService.getVacanciesByEmployer(employerId);
    }

    @PostMapping
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.updateVacancy(vacancyDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteVacancy(@PathVariable int id) {
        vacancyService.deleteVacancy(id);
        return HttpStatus.OK;
    }

    @PutMapping("{id}/status")
    public HttpStatus toggleVacancyStatus(@PathVariable int id, @RequestParam boolean isActive) {
        vacancyService.toggleVacancyStatus(id, isActive);
        return HttpStatus.OK;
    }
}