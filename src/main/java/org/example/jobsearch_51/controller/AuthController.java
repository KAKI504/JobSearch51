package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("register")
    public HttpStatus register(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return HttpStatus.CREATED;
    }
}