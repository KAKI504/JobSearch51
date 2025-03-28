package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.ResumeDto;
import org.example.jobsearch_51.exceptions.ResumeNotFoundException;
import org.example.jobsearch_51.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
@Validated
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public List<ResumeDto> getAllResumes() {
        log.info("Requesting all resumes");
        return resumeService.getAllResumes();
    }

    @GetMapping("active")
    public List<ResumeDto> getActiveResumes() {
        log.info("Requesting all active resumes");
        return resumeService.getActiveResumes();
    }

    @GetMapping("{id}")
    public ResumeDto getResumeById(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        log.info("Requesting resume with id: {}", id);
        ResumeDto resume = resumeService.getResumeById(id);
        if (resume == null) {
            throw new ResumeNotFoundException(id);
        }
        return resume;
    }

    @GetMapping("category/{categoryId}")
    public List<ResumeDto> getResumesByCategory(
            @PathVariable @Min(value = 1, message = "ID категории должен быть положительным числом") int categoryId) {
        log.info("Requesting resumes for category id: {}", categoryId);
        return resumeService.getResumesByCategory(categoryId);
    }

    @GetMapping("applicant/{applicantId}")
    public List<ResumeDto> getResumesByApplicant(
            @PathVariable @Min(value = 1, message = "ID соискателя должен быть положительным числом") int applicantId) {
        log.info("Requesting resumes for applicant id: {}", applicantId);
        return resumeService.getResumesByApplicant(applicantId);
    }

    @PostMapping
    public HttpStatus createResume(@Valid @RequestBody ResumeDto resumeDto) {
        log.info("Creating new resume for applicant id: {}", resumeDto.getApplicantId());
        resumeService.createResume(resumeDto);
        log.info("Resume created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateResume(@Valid @RequestBody ResumeDto resumeDto) {
        log.info("Updating resume with id: {}", resumeDto.getId());
        resumeService.updateResume(resumeDto);
        log.info("Resume updated successfully");
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteResume(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        log.info("Deleting resume with id: {}", id);
        resumeService.deleteResume(id);
        log.info("Resume deleted successfully");
        return HttpStatus.OK;
    }

    @PutMapping("{id}/status")
    public HttpStatus toggleResumeStatus(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id,
            @RequestParam boolean isActive) {
        log.info("Toggling resume status to {} for id: {}", isActive, id);
        resumeService.toggleResumeStatus(id, isActive);
        log.info("Resume status toggled successfully");
        return HttpStatus.OK;
    }
}