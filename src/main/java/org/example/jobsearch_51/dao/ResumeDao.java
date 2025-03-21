package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Resume;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Resume> getAllResumes() {
        String sql = "SELECT * FROM resumes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resume resume = new Resume();
            resume.setId(rs.getInt("id"));
            resume.setApplicantId(rs.getInt("applicant_id"));
            resume.setName(rs.getString("name"));
            resume.setCategoryId(rs.getInt("category_id"));
            resume.setSalary(rs.getString("salary"));
            resume.setActive(rs.getBoolean("is_active"));
            resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return resume;
        });
    }

    public List<Resume> getActiveResumes() {
        String sql = "SELECT * FROM resumes WHERE is_active = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resume resume = new Resume();
            resume.setId(rs.getInt("id"));
            resume.setApplicantId(rs.getInt("applicant_id"));
            resume.setName(rs.getString("name"));
            resume.setCategoryId(rs.getInt("category_id"));
            resume.setSalary(rs.getString("salary"));
            resume.setActive(rs.getBoolean("is_active"));
            resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return resume;
        });
    }

    public List<Resume> getResumesByCategory(int categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = ? AND is_active = true";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resume resume = new Resume();
            resume.setId(rs.getInt("id"));
            resume.setApplicantId(rs.getInt("applicant_id"));
            resume.setName(rs.getString("name"));
            resume.setCategoryId(rs.getInt("category_id"));
            resume.setSalary(rs.getString("salary"));
            resume.setActive(rs.getBoolean("is_active"));
            resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return resume;
        }, categoryId);
    }

    public List<Resume> getResumesByApplicant(int applicantId) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resume resume = new Resume();
            resume.setId(rs.getInt("id"));
            resume.setApplicantId(rs.getInt("applicant_id"));
            resume.setName(rs.getString("name"));
            resume.setCategoryId(rs.getInt("category_id"));
            resume.setSalary(rs.getString("salary"));
            resume.setActive(rs.getBoolean("is_active"));
            resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return resume;
        }, applicantId);
    }

    public Optional<Resume> getResumeById(int id) {
        String sql = "SELECT * FROM resumes WHERE id = ?";
        try {
            List<Resume> resumes = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Resume resume = new Resume();
                resume.setId(rs.getInt("id"));
                resume.setApplicantId(rs.getInt("applicant_id"));
                resume.setName(rs.getString("name"));
                resume.setCategoryId(rs.getInt("category_id"));
                resume.setSalary(rs.getString("salary"));
                resume.setActive(rs.getBoolean("is_active"));
                resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                return resume;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(resumes));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int createResume(Resume resume) {
        String sql = "INSERT INTO resumes (applicant_id, name, category_id, salary, is_active) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, resume.getApplicantId());
                    ps.setString(2, resume.getName());
                    ps.setInt(3, resume.getCategoryId());
                    ps.setString(4, resume.getSalary());
                    ps.setBoolean(5, resume.isActive());
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateResume(Resume resume) {
        String sql = "UPDATE resumes SET name = ?, category_id = ?, salary = ?, is_active = ?, update_time = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                Timestamp.valueOf(LocalDateTime.now()),
                resume.getId());
    }

    public void updateResumeStatus(int resumeId, boolean isActive) {
        String sql = "UPDATE resumes SET is_active = ?, update_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, isActive, Timestamp.valueOf(LocalDateTime.now()), resumeId);
    }

    public void deleteResume(int resumeId) {
        String sql = "DELETE FROM resumes WHERE id = ?";
        jdbcTemplate.update(sql, resumeId);
    }
}