package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.Vacancy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Vacancy> getAllVacancies() {
        String sql = "SELECT * FROM vacancies ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        });
    }

    public List<Vacancy> getActiveVacancies() {
        String sql = "SELECT * FROM vacancies WHERE is_active = true ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        });
    }

    public List<Vacancy> searchVacancies(String query) {
        String sql = "SELECT * FROM vacancies WHERE is_active = true AND LOWER(name) LIKE LOWER(?) ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        }, "%" + query + "%");
    }

    public List<Vacancy> getVacanciesByCategory(int categoryId) {
        String sql = "SELECT * FROM vacancies WHERE category_id = ? AND is_active = true ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        }, categoryId);
    }

    public List<Vacancy> getVacanciesByEmployer(int employerId) {
        String sql = "SELECT * FROM vacancies WHERE author_id = ? ORDER BY update_time DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        }, employerId);
    }

    public Optional<Vacancy> getVacancyById(int id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        try {
            List<Vacancy> vacancies = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Vacancy vacancy = new Vacancy();
                vacancy.setId(rs.getInt("id"));
                vacancy.setName(rs.getString("name"));
                vacancy.setDescription(rs.getString("description"));
                vacancy.setCategoryId(rs.getInt("category_id"));
                vacancy.setSalary(rs.getString("salary"));
                vacancy.setExpFrom(rs.getInt("exp_from"));
                vacancy.setExpTo(rs.getInt("exp_to"));
                vacancy.setActive(rs.getBoolean("is_active"));
                vacancy.setAuthorId(rs.getInt("author_id"));
                vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                return vacancy;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(vacancies));
        } catch (EmptyResultDataAccessException e) {
            log.error("Vacancy not found with id: {}", id, e);
            return Optional.empty();
        }
    }

    public int createVacancy(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, vacancy.getName());
                    ps.setString(2, vacancy.getDescription());
                    ps.setInt(3, vacancy.getCategoryId());
                    ps.setString(4, vacancy.getSalary());
                    ps.setInt(5, vacancy.getExpFrom());
                    ps.setInt(6, vacancy.getExpTo());
                    ps.setBoolean(7, vacancy.isActive());
                    ps.setInt(8, vacancy.getAuthorId());
                    return ps;
                }, keyHolder
        );
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created new vacancy with id: {}", id);
        return id;
    }

    public void updateVacancy(Vacancy vacancy) {
        String sql = "UPDATE vacancies SET name = ?, description = ?, category_id = ?, " +
                "salary = ?, exp_from = ?, exp_to = ?, is_active = ?, update_time = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isActive(),
                Timestamp.valueOf(LocalDateTime.now()),
                vacancy.getId());
        log.info("Updated vacancy with id: {}", vacancy.getId());
    }

    public void updateVacancyStatus(int vacancyId, boolean isActive) {
        String sql = "UPDATE vacancies SET is_active = ?, update_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, isActive, Timestamp.valueOf(LocalDateTime.now()), vacancyId);
        log.info("Updated vacancy status with id: {}", vacancyId);
    }

    public void deleteVacancy(int vacancyId) {
        String sql = "DELETE FROM vacancies WHERE id = ?";
        jdbcTemplate.update(sql, vacancyId);
        log.info("Deleted vacancy with id: {}", vacancyId);
    }

    public List<Vacancy> getVacanciesWithPagination(int page, int size) {
        String sql = "SELECT * FROM vacancies WHERE is_active = true ORDER BY update_time DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(rs.getInt("id"));
            vacancy.setName(rs.getString("name"));
            vacancy.setDescription(rs.getString("description"));
            vacancy.setCategoryId(rs.getInt("category_id"));
            vacancy.setSalary(rs.getString("salary"));
            vacancy.setExpFrom(rs.getInt("exp_from"));
            vacancy.setExpTo(rs.getInt("exp_to"));
            vacancy.setActive(rs.getBoolean("is_active"));
            vacancy.setAuthorId(rs.getInt("author_id"));
            vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return vacancy;
        }, size, (page - 1) * size);
    }

    public int getTotalVacanciesCount() {
        String sql = "SELECT COUNT(*) FROM vacancies WHERE is_active = true";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}