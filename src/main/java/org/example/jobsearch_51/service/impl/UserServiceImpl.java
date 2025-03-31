package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.UserAlreadyExistsException;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.model.User;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Retrieving all users");
        List<UserDto> users = userDao.getAllUsers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        log.info("Retrieved {} users", users.size());
        return users;
    }

    @Override
    public UserDto getUserById(int id) {
        log.info("Retrieving user with id: {}", id);
        if (id <= 0) {
            log.error("Invalid user ID: {}", id);
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        UserDto user = userDao.getUserById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new UserNotFoundException(id);
                });
        log.info("Successfully retrieved user with id: {}", id);
        return user;
    }

    @Override
    public void createUser(UserDto userDto) {
        log.info("Creating new user with email: {}", userDto.getEmail());
        if (userDao.checkIfUserExists(userDto.getEmail())) {
            log.error("User with email {} already exists", userDto.getEmail());
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        userDao.createUser(user);
        log.info("User created successfully with email: {}", user.getEmail());
    }

    @Override
    public void updateUser(UserDto userDto) {
        log.info("Updating user with id: {}", userDto.getId());
        if (userDto.getId() <= 0) {
            log.error("Invalid user ID: {}", userDto.getId());
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        User existingUser = userDao.getUserById(userDto.getId())
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userDto.getId());
                    return new UserNotFoundException(userDto.getId());
                });

        if (!existingUser.getEmail().equals(userDto.getEmail()) && userDao.checkIfUserExists(userDto.getEmail())) {
            log.error("Email {} is already in use by another user", userDto.getEmail());
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = convertToEntity(userDto);
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userDao.updateUser(user);
        log.info("User updated successfully with id: {}", user.getId());
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        log.info("Uploading avatar: {}", file.getOriginalFilename());
        String filename = fileService.saveImage(file);
        log.info("Avatar uploaded successfully with filename: {}", filename);
        return filename;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Retrieving user with email: {}", email);
        if (email == null || email.isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        UserDto user = userDao.getUserByEmail(email)
                .map(this::convertToDto)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new UserNotFoundException("User not found with email: " + email);
                });
        log.info("Successfully retrieved user with email: {}", email);
        return user;
    }

    @Override
    public UserDto getEmployerById(int id) {
        log.info("Retrieving employer with id: {}", id);
        if (id <= 0) {
            log.error("Invalid employer ID: {}", id);
            throw new IllegalArgumentException("ID работодателя должен быть положительным числом");
        }

        User user = userDao.getUserById(id)
                .orElseThrow(() -> {
                    log.error("Employer not found with id: {}", id);
                    return new UserNotFoundException(id);
                });

        if (!"EMPLOYER".equals(user.getAccountType())) {
            log.error("User with ID {} is not an employer", id);
            throw new IllegalArgumentException("Пользователь с ID " + id + " не является работодателем");
        }

        log.info("Successfully retrieved employer with id: {}", id);
        return convertToDto(user);
    }

    @Override
    public boolean checkIfUserExists(String email) {
        log.info("Checking if user exists with email: {}", email);
        if (email == null || email.isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        boolean exists = userDao.checkIfUserExists(email);
        log.info("User with email {} exists: {}", email, exists);
        return exists;
    }

    @Override
    public UserDto getApplicantById(int id) {
        log.info("Retrieving applicant with id: {}", id);
        if (id <= 0) {
            log.error("Invalid applicant ID: {}", id);
            throw new IllegalArgumentException("ID соискателя должен быть положительным числом");
        }

        User user = userDao.getUserById(id)
                .orElseThrow(() -> {
                    log.error("Applicant not found with id: {}", id);
                    return new UserNotFoundException(id);
                });

        if (!"APPLICANT".equals(user.getAccountType())) {
            log.error("User with ID {} is not an applicant", id);
            throw new IllegalArgumentException("Пользователь с ID " + id + " не является соискателем");
        }

        log.info("Successfully retrieved applicant with id: {}", id);
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .accountType(user.getAccountType())
                .age(user.getAge())
                .build();
    }

    private User convertToEntity(UserDto userDto) {
        User.UserBuilder builder = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .accountType(userDto.getAccountType())
                .age(userDto.getAge())
                .enabled(true);

        if (userDto.getId() != null) {
            builder.id(userDto.getId());
        }

        return builder.build();
    }
}