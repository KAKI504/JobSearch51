package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.AccessDeniedException;
import org.example.jobsearch_51.exceptions.ResponseNotFoundException;
import org.example.jobsearch_51.service.ResponseService;
import org.example.jobsearch_51.service.ResumeService;
import org.example.jobsearch_51.service.VacancyService;
import org.example.jobsearch_51.util.SecurityUtil;
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
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final SecurityUtil securityUtil;

    @GetMapping("vacancy/{vacancyId}")
    public List<ResponseDto> getResponsesByVacancy(
            @PathVariable @Min(value = 1, message = "ID вакансии должен быть положительным числом") int vacancyId) {
        UserDto currentUser = securityUtil.getCurrentUser();
        var vacancy = vacancyService.getVacancyById(vacancyId);

        if (vacancy.getAuthorId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to view responses by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно просматривать отклики только на собственные вакансии");
        }

        log.info("Requesting responses for vacancy id: {}", vacancyId);
        return responseService.getResponsesByVacancy(vacancyId);
    }

    @GetMapping("resume/{resumeId}")
    public List<ResponseDto> getResponsesByResume(
            @PathVariable @Min(value = 1, message = "ID резюме должен быть положительным числом") int resumeId) {
        UserDto currentUser = securityUtil.getCurrentUser();
        var resume = resumeService.getResumeById(resumeId);

        if (resume.getApplicantId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to view responses by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно просматривать отклики только на собственные резюме");
        }

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

        UserDto currentUser = securityUtil.getCurrentUser();
        var resume = resumeService.getResumeById(response.getResumeId());
        var vacancy = vacancyService.getVacancyById(response.getVacancyId());

        if (resume.getApplicantId() != currentUser.getId() &&
                vacancy.getAuthorId() != currentUser.getId() &&
                !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to view response by user: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: нет прав для просмотра этого отклика");
        }

        return response;
    }

    @PostMapping
    public HttpStatus createResponse(@Valid @RequestBody ResponseDto responseDto) {
        UserDto currentUser = securityUtil.getCurrentUser();
        var resume = resumeService.getResumeById(responseDto.getResumeId());

        if (resume.getApplicantId() != currentUser.getId()) {
            log.warn("Unauthorized attempt to create response with resume id {}: {}",
                    responseDto.getResumeId(), currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: можно использовать только собственные резюме для отклика");
        }

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
        UserDto currentUser = securityUtil.getCurrentUser();
        ResponseDto response = responseService.getResponseById(id);
        var vacancy = vacancyService.getVacancyById(response.getVacancyId());

        if (vacancy.getAuthorId() != currentUser.getId() && !"ADMIN".equals(currentUser.getAccountType())) {
            log.warn("Unauthorized attempt to confirm response: {}", currentUser.getEmail());
            throw new AccessDeniedException("Доступ запрещен: только работодатель может подтвердить отклик");
        }

        log.info("Updating confirmation status to {} for response id: {}", confirmation, id);
        responseService.updateResponseConfirmation(id, confirmation);
        log.info("Response confirmation status updated successfully");
        return HttpStatus.OK;
    }
}