package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.MessageDto;
import org.example.jobsearch_51.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("response/{responseId}")
    public List<MessageDto> getMessagesByResponse(@PathVariable int responseId) {
        log.info("Requesting messages for response id: {}", responseId);
        return messageService.getMessagesByResponse(responseId);
    }

    @PostMapping
    public HttpStatus createMessage(@Valid @RequestBody MessageDto messageDto) {
        log.info("Creating message for response id: {} from sender id: {}",
                messageDto.getResponseId(), messageDto.getSenderId());
        messageService.createMessage(messageDto);
        log.info("Message created successfully");
        return HttpStatus.CREATED;
    }
}