package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.exceptions.ResponseNotFoundException;
import org.example.jobsearch_51.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("responses")
@RequiredArgsConstructor
@Validated
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping("vacancy/{vacancyId}")
    public List<ResponseDto> getResponsesByVacancy(
            @PathVariable @Min(value = 1, message = "ID вакансии должен быть положительным числом") int vacancyId) {
        log.info("Requesting responses for vacancy id: {}", vacancyId);
        return responseService.getResponsesByVacancy(vacancyId);
    }

    @GetMapping("resume/{resumeId}")
    public List<ResponseDto> getResponsesByResume(
            @PathVariable @Min(value = 1, message = "ID резюме должен быть положительным числом") int resumeId) {
        log.info("Requesting responses for resume id: {}", resumeId);
        return responseService.getResponsesByResume(resumeId);
    }

    @GetMapping("{id}")
    public ResponseDto getResponseById(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        log.info("Requesting response with id: {}", id);
        ResponseDto response = responseService.getResponseById(id);
        if (response == null) {
            throw new ResponseNotFoundException(id);
        }
        return response;
    }

    @PostMapping
    public HttpStatus createResponse(@Valid @RequestBody ResponseDto responseDto) {
        log.info("Creating response for resume id: {} to vacancy id: {}",
                responseDto.getResumeId(), responseDto.getVacancyId());
        responseService.createResponse(responseDto);
        log.info("Response created successfully");
        return HttpStatus.CREATED;
    }

    @PutMapping("{id}/confirm")
    public HttpStatus confirmResponse(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id,
            @RequestParam boolean confirmation) {
        log.info("Updating confirmation status to {} for response id: {}", confirmation, id);
        responseService.updateResponseConfirmation(id, confirmation);
        log.info("Response confirmation status updated successfully");
        return HttpStatus.OK;
    }
}