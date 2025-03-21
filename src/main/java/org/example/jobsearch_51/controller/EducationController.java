package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.EducationDto;
import org.example.jobsearch_51.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @GetMapping("resume/{resumeId}")
    public List<EducationDto> getEducationsByResume(@PathVariable int resumeId) {
        return educationService.getEducationsByResume(resumeId);
    }

    @GetMapping("{id}")
    public EducationDto getEducationById(@PathVariable int id) {
        return educationService.getEducationById(id);
    }

    @PostMapping
    public HttpStatus createEducation(@RequestBody EducationDto educationDto) {
        educationService.createEducation(educationDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateEducation(@RequestBody EducationDto educationDto) {
        educationService.updateEducation(educationDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteEducation(@PathVariable int id) {
        educationService.deleteEducation(id);
        return HttpStatus.OK;
    }
}