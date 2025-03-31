package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAvatar(rs.getString("avatar"));
            user.setAccountType(rs.getString("account_type"));
            user.setAge(rs.getInt("age"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        });
    }

    public Optional<User> getUserById(int id) {
        if (id <= 0) {
            log.error("Invalid user ID: {}", id);
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatar(rs.getString("avatar"));
                user.setAccountType(rs.getString("account_type"));
                user.setAge(rs.getInt("age"));
                user.setEnabled(rs.getBoolean("enabled"));
                return user;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(users));
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found with ID: {}", id, e);
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAvatar(rs.getString("avatar"));
                user.setAccountType(rs.getString("account_type"));
                user.setAge(rs.getInt("age"));
                user.setEnabled(rs.getBoolean("enabled"));
                return user;
            }, email);
            return Optional.ofNullable(DataAccessUtils.singleResult(users));
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found with email: {}", email, e);
            return Optional.empty();
        }
    }

    public int createUser(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            log.error("Invalid user name: null or empty");
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            log.error("Invalid password: null or empty");
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }

        if (user.getAccountType() == null || user.getAccountType().trim().isEmpty()) {
            log.error("Invalid account type: null or empty");
            throw new IllegalArgumentException("Тип аккаунта не может быть пустым");
        }

        if (user.getAge() < 0) {
            log.error("Invalid age: {}", user.getAge());
            throw new IllegalArgumentException("Возраст не может быть отрицательным");
        }

        if (checkIfUserExists(user.getEmail())) {
            log.error("User with email {} already exists", user.getEmail());
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        String sql = "INSERT INTO users (name, surname, email, password, phone_number, account_type, age, enabled) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getSurname());
                    ps.setString(3, user.getEmail());
                    ps.setString(4, user.getPassword());
                    ps.setString(5, user.getPhoneNumber());
                    ps.setString(6, user.getAccountType());
                    ps.setInt(7, user.getAge());
                    ps.setBoolean(8, user.isEnabled());
                    return ps;
                }, keyHolder
        );

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created user with ID: {}", id);
        return id;
    }

    public void updateUser(User user) {
        if (user.getId() <= 0) {
            log.error("Invalid user ID: {}", user.getId());
            throw new IllegalArgumentException("ID пользователя должен быть положительным числом");
        }

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            log.error("Invalid user name: null or empty");
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        if (user.getAge() < 0) {
            log.error("Invalid age: {}", user.getAge());
            throw new IllegalArgumentException("Возраст не может быть отрицательным");
        }

        String sql = "UPDATE users SET name = ?, surname = ?, email = ?, password = ?, phone_number = ?, account_type = ?, age = ?, enabled = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAccountType(),
                user.getAge(),
                user.isEnabled(),
                user.getId());

        if (rowsUpdated == 0) {
            log.warn("No user updated with ID: {}", user.getId());
        } else {
            log.info("Updated user with ID: {}", user.getId());
        }
    }

    public boolean checkIfUserExists(String email) {
        if (email == null || email.isEmpty()) {
            log.error("Invalid email: null or empty");
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}