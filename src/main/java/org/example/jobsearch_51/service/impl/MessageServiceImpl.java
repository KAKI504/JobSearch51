package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.MessageDao;
import org.example.jobsearch_51.dao.ResponseDao;
import org.example.jobsearch_51.dto.MessageDto;
import org.example.jobsearch_51.exceptions.ResponseNotFoundException;
import org.example.jobsearch_51.model.Message;
import org.example.jobsearch_51.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final ResponseDao responseDao;

    @Override
    public List<MessageDto> getMessagesByResponse(int responseId) {
        if (responseId <= 0) {
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        if (!responseDao.getResponseById(responseId).isPresent()) {
            throw new ResponseNotFoundException(responseId);
        }

        return messageDao.getMessagesByResponseId(responseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createMessage(MessageDto messageDto) {
        if (messageDto.getResponseId() <= 0) {
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        if (messageDto.getSenderId() <= 0) {
            throw new IllegalArgumentException("ID отправителя должен быть положительным числом");
        }

        if (!responseDao.getResponseById(messageDto.getResponseId()).isPresent()) {
            throw new ResponseNotFoundException(messageDto.getResponseId());
        }

        Message message = convertToEntity(messageDto);
        messageDao.createMessage(message);
    }

    private MessageDto convertToDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .responseId(message.getResponseId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .senderId(message.getSenderId())
                .build();
    }

    private Message convertToEntity(MessageDto messageDto) {
        Message message = new Message();
        message.setId(messageDto.getId());
        message.setResponseId(messageDto.getResponseId());
        message.setContent(messageDto.getContent());

        if (messageDto.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        } else {
            message.setTimestamp(messageDto.getTimestamp());
        }

        message.setSenderId(messageDto.getSenderId());
        return message;
    }
}