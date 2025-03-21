package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping("vacancy/{vacancyId}")
    public List<ResponseDto> getResponsesByVacancy(@PathVariable int vacancyId) {
        return responseService.getResponsesByVacancy(vacancyId);
    }

    @GetMapping("resume/{resumeId}")
    public List<ResponseDto> getResponsesByResume(@PathVariable int resumeId) {
        return responseService.getResponsesByResume(resumeId);
    }

    @GetMapping("{id}")
    public ResponseDto getResponseById(@PathVariable int id) {
        return responseService.getResponseById(id);
    }

    @PostMapping
    public HttpStatus createResponse(@RequestBody ResponseDto responseDto) {
        responseService.createResponse(responseDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("{id}/confirm")
    public HttpStatus confirmResponse(@PathVariable int id, @RequestParam boolean confirmation) {
        responseService.updateResponseConfirmation(id, confirmation);
        return HttpStatus.OK;
    }
}