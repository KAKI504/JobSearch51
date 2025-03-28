package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.FileProcessingException;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Requesting all users");
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable int id) {
        log.info("Requesting user with id: {}", id);
        UserDto user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @GetMapping("email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        log.info("Requesting user with email: {}", email);
        UserDto user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    @PostMapping
    public HttpStatus createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating new user with email: {}", userDto.getEmail());
        userService.createUser(userDto);
        log.info("User created successfully with email: {}", userDto.getEmail());
        return HttpStatus.CREATED;
    }

    @PutMapping
    public HttpStatus updateUser(@Valid @RequestBody UserDto userDto) {
        log.info("Updating user with id: {}", userDto.getId());
        userService.updateUser(userDto);
        log.info("User updated successfully with id: {}", userDto.getId());
        return HttpStatus.OK;
    }

    @PostMapping("avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        log.info("Uploading avatar");
        if (file == null || file.isEmpty()) {
            throw new FileProcessingException("Файл не может быть пустым");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new FileProcessingException("Загружаемый файл должен быть изображением");
        }

        String filename = userService.uploadAvatar(file);
        log.info("Avatar uploaded successfully: {}", filename);
        return filename;
    }
}