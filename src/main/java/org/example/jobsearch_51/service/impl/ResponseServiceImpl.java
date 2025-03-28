package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.ResumeDao;
import org.example.jobsearch_51.dao.ResponseDao;
import org.example.jobsearch_51.dao.VacancyDao;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.exceptions.ResumeNotFoundException;
import org.example.jobsearch_51.exceptions.ResponseNotFoundException;
import org.example.jobsearch_51.exceptions.VacancyNotFoundException;
import org.example.jobsearch_51.model.Response;
import org.example.jobsearch_51.service.ResponseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseDao responseDao;
    private final ResumeDao resumeDao;
    private final VacancyDao vacancyDao;

    @Override
    public List<ResponseDto> getResponsesByVacancy(int vacancyId) {
        if (vacancyId <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        if (!vacancyDao.getVacancyById(vacancyId).isPresent()) {
            throw new VacancyNotFoundException(vacancyId);
        }

        return responseDao.getResponsesByVacancyId(vacancyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseDto> getResponsesByResume(int resumeId) {
        if (resumeId <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeDao.getResumeById(resumeId).isPresent()) {
            throw new ResumeNotFoundException(resumeId);
        }

        return responseDao.getResponsesByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto getResponseById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        return responseDao.getResponseById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseNotFoundException(id));
    }

    @Override
    public void createResponse(ResponseDto responseDto) {
        if (responseDto.getResumeId() <= 0) {
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (responseDto.getVacancyId() <= 0) {
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        if (!resumeDao.getResumeById(responseDto.getResumeId()).isPresent()) {
            throw new ResumeNotFoundException(responseDto.getResumeId());
        }

        if (!vacancyDao.getVacancyById(responseDto.getVacancyId()).isPresent()) {
            throw new VacancyNotFoundException(responseDto.getVacancyId());
        }

        Response response = convertToEntity(responseDto);
        responseDao.createResponse(response);
    }

    @Override
    public void updateResponseConfirmation(int responseId, boolean confirmation) {
        if (responseId <= 0) {
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        if (!responseDao.getResponseById(responseId).isPresent()) {
            throw new ResponseNotFoundException(responseId);
        }

        responseDao.updateResponseConfirmation(responseId, confirmation);
    }

    private ResponseDto convertToDto(Response response) {
        return ResponseDto.builder()
                .id(response.getId())
                .resumeId(response.getResumeId())
                .vacancyId(response.getVacancyId())
                .confirmation(response.isConfirmation())
                .build();
    }

    private Response convertToEntity(ResponseDto responseDto) {
        Response response = new Response();
        response.setId(responseDto.getId());
        response.setResumeId(responseDto.getResumeId());
        response.setVacancyId(responseDto.getVacancyId());
        response.setConfirmation(responseDto.isConfirmation());
        return response;
    }
}