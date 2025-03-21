package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.MessageDto;
import org.example.jobsearch_51.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("response/{responseId}")
    public List<MessageDto> getMessagesByResponse(@PathVariable int responseId) {
        return messageService.getMessagesByResponse(responseId);
    }

    @PostMapping
    public HttpStatus createMessage(@RequestBody MessageDto messageDto) {
        messageService.createMessage(messageDto);
        return HttpStatus.CREATED;
    }
}