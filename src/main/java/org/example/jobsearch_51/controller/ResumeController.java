package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.ResumeDto;
import org.example.jobsearch_51.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public List<ResumeDto> getAllResumes() {
        return resumeService.getAllResumes();
    }

    @GetMapping("active")
    public List<ResumeDto> getActiveResumes() {
        return resumeService.getActiveResumes();
    }

    @GetMapping("{id}")
    public ResumeDto getResumeById(@PathVariable int id) {
        return resumeService.getResumeById(id);
    }

    @GetMapping("category/{categoryId}")
    public List<ResumeDto> getResumesByCategory(@PathVariable int categoryId) {
        return resumeService.getResumesByCategory(categoryId);
    }

    @GetMapping("applicant/{applicantId}")
    public List<ResumeDto> getResumesByApplicant(@PathVariable int applicantId) {
        return resumeService.getResumesByApplicant(applicantId);
    }

    @PostMapping
    public HttpStatus createResume(@RequestBody ResumeDto resumeDto) {
        resumeService.createResume(resumeDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateResume(@RequestBody ResumeDto resumeDto) {
        resumeService.updateResume(resumeDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}")
    public HttpStatus deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        return HttpStatus.OK;
    }

    @PutMapping("{id}/status")
    public HttpStatus toggleResumeStatus(@PathVariable int id, @RequestParam boolean isActive) {
        resumeService.toggleResumeStatus(id, isActive);
        return HttpStatus.OK;
    }
}