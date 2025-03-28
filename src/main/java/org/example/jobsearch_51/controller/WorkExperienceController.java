package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.WorkExperienceDto;
import org.example.jobsearch_51.exceptions.WorkExperienceNotFoundException;
import org.example.jobsearch_51.service.WorkExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;

    @GetMapping("resume/{resumeId}")
    public List<WorkExperienceDto> getWorkExperiencesByResume(@PathVariable int resumeId) {
        log.info("Requesting work experience records for resume id: {}", resumeId);
        return workExperienceService.getWorkExperiencesByResume(resumeId);
    }

    @GetMapping("{id}")
    public WorkExperienceDto getWorkExperienceById(@PathVariable int id) {
        log.info("Requesting work experience with id: {}", id);
        WorkExperienceDto workExperience = workExperienceService.getWorkExperienceById(id);
        if (workExperience == null) {
            throw new WorkExperienceNotFoundException(id);
        }
        return workExperience;
    }

    @PostMapping
    public HttpStatus createWorkExperience(@Valid @RequestBody WorkExperienceDto workExperienceDto) {
        log.info("Creating work experience record for resume id: {}", workExperienceDto.getResumeId());
        workExperienceService.createWorkExperience(workExperienceDto);
        log.info("Work experience record created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateWorkExperience(@Valid @RequestBody WorkExperienceDto workExperienceDto) {
        log.info("Updating work experience record with id: {}", workExperienceDto.getId());
        workExperienceService.updateWorkExperience(workExperienceDto);
        log.info("Work experience record updated successfully");
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteWorkExperience(@PathVariable int id) {
        log.info("Deleting work experience record with id: {}", id);
        workExperienceService.deleteWorkExperience(id);
        log.info("Work experience record deleted successfully");
        return HttpStatus.OK;
    }
}