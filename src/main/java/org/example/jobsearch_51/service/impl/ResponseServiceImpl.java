package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.ResponseDao;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.model.Response;
import org.example.jobsearch_51.service.ResponseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseDao responseDao;

    @Override
    public List<ResponseDto> getResponsesByVacancy(int vacancyId) {
        return responseDao.getResponsesByVacancyId(vacancyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseDto> getResponsesByResume(int resumeId) {
        return responseDao.getResponsesByResumeId(resumeId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto getResponseById(int id) {
        return responseDao.getResponseById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Response not found with id: " + id));
    }

    @Override
    public void createResponse(ResponseDto responseDto) {
        Response response = convertToEntity(responseDto);
        responseDao.createResponse(response);
    }

    @Override
    public void updateResponseConfirmation(int responseId, boolean confirmation) {
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