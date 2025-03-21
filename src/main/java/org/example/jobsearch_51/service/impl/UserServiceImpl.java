package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dto.UserDto;
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
        return userDao.getUserById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.createUser(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        userDao.updateUser(user);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        return fileService.saveImage(file);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userDao.getUserByEmail(email)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
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
}