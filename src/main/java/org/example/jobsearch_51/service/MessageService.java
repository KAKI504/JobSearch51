package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getMessagesByResponse(int responseId);
    void createMessage(MessageDto messageDto);
}