package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.dao.ProfileDao;
import org.example.jobsearch_51.dao.UserDao;
import org.example.jobsearch_51.dto.UserDto;
import org.example.jobsearch_51.exceptions.ProfileUpdateException;
import org.example.jobsearch_51.exceptions.UserNotFoundException;
import org.example.jobsearch_51.model.User;
import org.example.jobsearch_51.service.FileService;
import org.example.jobsearch_51.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileDao profileDao;
    private final UserDao userDao;
    private final FileService fileService;

    @Override
    public UserDto getUserProfile(int userId) {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return convertToDto(user);
    }

    @Override
    public void updateUserProfile(UserDto userDto) {
        User user = userDao.getUserById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userDto.getId()));

        if (!userDto.getEmail().equals(user.getEmail()) && !isEmailAvailable(userDto.getEmail(), userDto.getId())) {
            throw new ProfileUpdateException("Email is already in use by another user");
        }

        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals(user.getPhoneNumber())
                && !isPhoneAvailable(userDto.getPhoneNumber(), userDto.getId())) {
            throw new ProfileUpdateException("Phone number is already in use by another user");
        }

        User updatedUser = convertToEntity(userDto);
        updatedUser.setPassword(user.getPassword());
        updatedUser.setAvatar(user.getAvatar());

        profileDao.updateUserProfile(updatedUser);
        log.info("User profile updated: {}", updatedUser.getId());
    }

    @Override
    public String updateUserAvatar(int userId, MultipartFile file) {
        if (!userDao.getUserById(userId).isPresent()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        String avatarFileName = fileService.saveImage(file);
        profileDao.updateUserAvatar(userId, avatarFileName);
        log.info("Avatar updated for user: {}", userId);
        return avatarFileName;
    }

    @Override
    public boolean updateUserPassword(int userId, String currentPassword, String newPassword) {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (!user.getPassword().equals(currentPassword)) {
            log.warn("Password update failed: current password doesn't match for user {}", userId);
            return false;
        }

        profileDao.updateUserPassword(userId, newPassword);
        log.info("Password updated for user: {}", userId);
        return true;
    }

    @Override
    public boolean isEmailAvailable(String email, int currentUserId) {
        return profileDao.isEmailAvailable(email, currentUserId);
    }

    @Override
    public boolean isPhoneAvailable(String phoneNumber, int currentUserId) {
        return profileDao.isPhoneAvailable(phoneNumber, currentUserId);
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
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAccountType(userDto.getAccountType());
        user.setAge(userDto.getAge());
        return user;
    }
}