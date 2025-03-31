package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    void createUser(UserDto userDto);
    void updateUser(UserDto userDto);
    String uploadAvatar(MultipartFile file);
    UserDto getUserByEmail(String email);
    UserDto getEmployerById(int id);
    UserDto getApplicantById(int id);
    boolean checkIfUserExists(String email);
}