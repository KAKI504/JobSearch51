package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            return user;
        });
    }

    public Optional<User> getUserById(int id) {
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
                return user;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(users));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByEmail(String email) {
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
                return user;
            }, email);
            return Optional.ofNullable(DataAccessUtils.singleResult(users));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByPhone(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number = ?";
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
                return user;
            }, phoneNumber);
            return Optional.ofNullable(DataAccessUtils.singleResult(users));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int createUser(User user) {
        String sql = "INSERT INTO users (name, surname, email, password, phone_number, account_type, age) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateUserAvatar(int userId, String avatarPath) {
        String sql = "UPDATE users SET avatar = ? WHERE id = ?";
        jdbcTemplate.update(sql, avatarPath, userId);
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, email = ?, phone_number = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getEmail(),
                user.getPhoneNumber(), user.getAge(), user.getId());
    }

    public boolean checkIfUserExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}