package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final FileService fileService;

    @Override
    public List<UserDto> getAllUsers() {
        return new ArrayList<>();
    }

    @Override
    public UserDto getUserById(int id) {
        return new UserDto();
    }

    @Override
    public void createUser(UserDto userDto) {
    }

    @Override
    public void updateUser(UserDto userDto) {
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        return fileService.saveImage(file);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return new UserDto();
    }
}