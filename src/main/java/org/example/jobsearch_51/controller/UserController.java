package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return HttpStatus.OK;
    }

    @PostMapping("avatar")
    public String uploadAvatar(MultipartFile file) {
        return userService.uploadAvatar(file);
    }
}