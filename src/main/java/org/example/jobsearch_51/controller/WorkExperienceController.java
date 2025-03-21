package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.WorkExperienceDto;
import org.example.jobsearch_51.service.WorkExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;

    @GetMapping("resume/{resumeId}")
    public List<WorkExperienceDto> getWorkExperiencesByResume(@PathVariable int resumeId) {
        return workExperienceService.getWorkExperiencesByResume(resumeId);
    }

    @GetMapping("{id}")
    public WorkExperienceDto getWorkExperienceById(@PathVariable int id) {
        return workExperienceService.getWorkExperienceById(id);
    }

    @PostMapping
    public HttpStatus createWorkExperience(@RequestBody WorkExperienceDto workExperienceDto) {
        workExperienceService.createWorkExperience(workExperienceDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateWorkExperience(@RequestBody WorkExperienceDto workExperienceDto) {
        workExperienceService.updateWorkExperience(workExperienceDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteWorkExperience(@PathVariable int id) {
        workExperienceService.deleteWorkExperience(id);
        return HttpStatus.OK;
    }
}