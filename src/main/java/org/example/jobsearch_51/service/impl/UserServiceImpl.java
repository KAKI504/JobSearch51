package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.UserAlreadyExistsException;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.model.User;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final FileService fileService;

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        return userDao.getUserById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void createUser(UserDto userDto) {
        if (userDao.checkIfUserExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = convertToEntity(userDto);
        userDao.createUser(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        if (userDto.getId() <= 0) {
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        if (!userDao.getUserById(userDto.getId()).isPresent()) {
            throw new UserNotFoundException(userDto.getId());
        }

        User existingUser = userDao.getUserById(userDto.getId()).orElseThrow(() -> new UserNotFoundException(userDto.getId()));
        if (!existingUser.getEmail().equals(userDto.getEmail()) && userDao.checkIfUserExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(userDto.getEmail());
        }

        User user = convertToEntity(userDto);
        userDao.updateUser(user);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        return fileService.saveImage(file);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        return userDao.getUserByEmail(email)
                .map(this::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .accountType(user.getAccountType())
                .age(user.getAge())
                .build();
    }

    private User convertToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phoneNumber(userDto.getPhoneNumber())
                .accountType(userDto.getAccountType())
                .age(userDto.getAge())
                .build();
    }
    @Override
    public UserDto getEmployerById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID работодателя должен быть положительным числом");
        }
        User user = userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!"EMPLOYER".equals(user.getAccountType())) {
            throw new IllegalArgumentException("Пользователь с ID " + id + " не является работодателем");
        }

        return convertToDto(user);
    }

    @Override
    public UserDto getApplicantById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID соискателя должен быть положительным числом");
        }
        User user = userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!"APPLICANT".equals(user.getAccountType())) {
            throw new IllegalArgumentException("Пользователь с ID " + id + " не является соискателем");
        }

        return convertToDto(user);
    }
}