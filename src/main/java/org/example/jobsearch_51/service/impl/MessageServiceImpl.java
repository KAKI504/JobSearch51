package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.MessageDao;
import org.example.jobsearch_51.dto.MessageDto;
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

    @Override
    public List<MessageDto> getMessagesByResponse(int responseId) {
        return messageDao.getMessagesByResponseId(responseId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createMessage(MessageDto messageDto) {
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