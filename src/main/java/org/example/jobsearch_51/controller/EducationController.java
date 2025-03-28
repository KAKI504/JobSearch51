package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.EducationDto;
import org.example.jobsearch_51.exceptions.EducationNotFoundException;
import org.example.jobsearch_51.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @GetMapping("resume/{resumeId}")
    public List<EducationDto> getEducationsByResume(@PathVariable int resumeId) {
        log.info("Requesting education records for resume id: {}", resumeId);
        return educationService.getEducationsByResume(resumeId);
    }

    @GetMapping("{id}")
    public EducationDto getEducationById(@PathVariable int id) {
        log.info("Requesting education with id: {}", id);
        EducationDto education = educationService.getEducationById(id);
        if (education == null) {
            throw new EducationNotFoundException(id);
        }
        return education;
    }

    @PostMapping
    public HttpStatus createEducation(@Valid @RequestBody EducationDto educationDto) {
        log.info("Creating education record for resume id: {}", educationDto.getResumeId());
        educationService.createEducation(educationDto);
        log.info("Education record created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateEducation(@Valid @RequestBody EducationDto educationDto) {
        log.info("Updating education record with id: {}", educationDto.getId());
        educationService.updateEducation(educationDto);
        log.info("Education record updated successfully");
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteEducation(@PathVariable int id) {
        log.info("Deleting education record with id: {}", id);
        educationService.deleteEducation(id);
        log.info("Education record deleted successfully");
        return HttpStatus.OK;
    }
}