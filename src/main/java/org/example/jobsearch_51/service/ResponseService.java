package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.ResponseDto;
import java.util.List;

public interface ResponseService {
    List<ResponseDto> getResponsesByVacancy(int vacancyId);
    List<ResponseDto> getResponsesByResume(int resumeId);
    ResponseDto getResponseById(int id);
    void createResponse(ResponseDto responseDto);
    void updateResponseConfirmation(int responseId, boolean confirmation);
}