package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfileDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDao userDao;

    public void updateUserProfile(User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, email = ?, phone_number = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAge(),
                user.getId());
        log.info("Profile updated for user: {}", user.getId());
    }

    public void updateUserAvatar(int userId, String avatarPath) {
        String sql = "UPDATE users SET avatar = ? WHERE id = ?";
        jdbcTemplate.update(sql, avatarPath, userId);
        log.info("Avatar updated for user: {}", userId);
    }

    public void updateUserPassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, newPassword, userId);
        log.info("Password updated for user: {}", userId);
    }

    public boolean isEmailAvailable(String email, int currentUserId) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email, currentUserId);
        return count != null && count == 0;
    }

    public boolean isPhoneAvailable(String phoneNumber, int currentUserId) {
        String sql = "SELECT COUNT(*) FROM users WHERE phone_number = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phoneNumber, currentUserId);
        return count != null && count == 0;
    }
}