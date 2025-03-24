package org.example.jobsearch_51.service;

import org.example.jobsearch_51.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    UserDto getUserProfile(int userId);

    void updateUserProfile(UserDto userDto);

    String updateUserAvatar(int userId, MultipartFile file);

    boolean updateUserPassword(int userId, String currentPassword, String newPassword);

    boolean isEmailAvailable(String email, int currentUserId);

    boolean isPhoneAvailable(String phoneNumber, int currentUserId);
}