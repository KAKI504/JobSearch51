package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public HttpStatus register(@Valid @RequestBody UserDto userDto) {
        log.info("Registration request received for email: {}", userDto.getEmail());
        userService.createUser(userDto);
        log.info("User successfully registered with email: {}", userDto.getEmail());
        return HttpStatus.CREATED;
    }
}