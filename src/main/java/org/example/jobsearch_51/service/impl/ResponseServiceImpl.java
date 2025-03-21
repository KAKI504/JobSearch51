package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.ResponseDto;
import org.example.jobsearch_51.service.ResponseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    @Override
    public List<ResponseDto> getResponsesByVacancy(int vacancyId) {
        return new ArrayList<>();
    }

    @Override
    public List<ResponseDto> getResponsesByResume(int resumeId) {
        return new ArrayList<>();
    }

    @Override
    public ResponseDto getResponseById(int id) {
        return new ResponseDto();
    }

    @Override
    public void createResponse(ResponseDto responseDto) {
    }

    @Override
    public void updateResponseConfirmation(int responseId, boolean confirmation) {
    }
}