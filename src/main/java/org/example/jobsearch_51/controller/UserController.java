package org.example.jobsearch_51.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.service.UserService;
import org.example.jobsearch_51.util.FileValidator;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final FileService fileService;
    private final FileValidator fileValidator;

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Requesting all users");
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserDto getUserById(
            @PathVariable @Min(value = 1, message = "ID должен быть положительным числом") int id) {
        log.info("Requesting user with id: {}", id);
        UserDto user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @GetMapping("email/{email}")
    public UserDto getUserByEmail(
            @PathVariable @Email(message = "Некорректный формат email") String email) {
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

    @GetMapping("employer/{id}")
    public UserDto getEmployerById(
            @PathVariable @Min(value = 1, message = "ID работодателя должен быть положительным числом") int id) {
        log.info("Requesting employer with id: {}", id);
        return userService.getEmployerById(id);
    }

    @GetMapping("applicant/{id}")
    public UserDto getApplicantById(
            @PathVariable @Min(value = 1, message = "ID соискателя должен быть положительным числом") int id) {
        log.info("Requesting applicant with id: {}", id);
        return userService.getApplicantById(id);
    }

    @PostMapping("avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        log.info("Uploading user avatar: {}", file.getOriginalFilename());
        fileValidator.validateImageFile(file);
        String filename = fileService.saveImage(file);
        log.info("User avatar uploaded successfully: {}", filename);
        return filename;
    }
}