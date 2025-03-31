package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class ResponseDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Response> getResponsesByVacancyId(int vacancyId) {
        if (vacancyId <= 0) {
            log.error("Invalid vacancy ID: {}", vacancyId);
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        String sql = "SELECT * FROM responded_applicants WHERE vacancy_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Response response = new Response();
            response.setId(rs.getInt("id"));
            response.setResumeId(rs.getInt("resume_id"));
            response.setVacancyId(rs.getInt("vacancy_id"));
            response.setConfirmation(rs.getBoolean("confirmation"));
            return response;
        }, vacancyId);
    }

    public List<Response> getResponsesByResumeId(int resumeId) {
        if (resumeId <= 0) {
            log.error("Invalid resume ID: {}", resumeId);
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        String sql = "SELECT * FROM responded_applicants WHERE resume_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Response response = new Response();
            response.setId(rs.getInt("id"));
            response.setResumeId(rs.getInt("resume_id"));
            response.setVacancyId(rs.getInt("vacancy_id"));
            response.setConfirmation(rs.getBoolean("confirmation"));
            return response;
        }, resumeId);
    }

    public Optional<Response> getResponseById(int id) {
        if (id <= 0) {
            log.error("Invalid response ID: {}", id);
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        String sql = "SELECT * FROM responded_applicants WHERE id = ?";
        try {
            List<Response> responses = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Response response = new Response();
                response.setId(rs.getInt("id"));
                response.setResumeId(rs.getInt("resume_id"));
                response.setVacancyId(rs.getInt("vacancy_id"));
                response.setConfirmation(rs.getBoolean("confirmation"));
                return response;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(responses));
        } catch (EmptyResultDataAccessException e) {
            log.error("Response not found with ID: {}", id, e);
            return Optional.empty();
        }
    }

    public int createResponse(Response response) {
        if (response.getResumeId() <= 0) {
            log.error("Invalid resume ID: {}", response.getResumeId());
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (response.getVacancyId() <= 0) {
            log.error("Invalid vacancy ID: {}", response.getVacancyId());
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        String sql = "INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, response.getResumeId());
                    ps.setInt(2, response.getVacancyId());
                    ps.setBoolean(3, response.isConfirmation());
                    return ps;
                }, keyHolder
        );

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created response with ID: {}", id);
        return id;
    }

    public void updateResponseConfirmation(int responseId, boolean confirmation) {
        if (responseId <= 0) {
            log.error("Invalid response ID: {}", responseId);
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        String sql = "UPDATE responded_applicants SET confirmation = ? WHERE id = ?";
        int rowsUpdated = jdbcTemplate.update(sql, confirmation, responseId);

        if (rowsUpdated == 0) {
            log.warn("No response confirmation updated with ID: {}", responseId);
        } else {
            log.info("Updated response confirmation for ID: {}, confirmation: {}", responseId, confirmation);
        }
    }

    public void deleteResponse(int responseId) {
        if (responseId <= 0) {
            log.error("Invalid response ID: {}", responseId);
            throw new IllegalArgumentException("ID отклика должен быть положительным числом");
        }

        String sql = "DELETE FROM responded_applicants WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, responseId);

        if (rowsDeleted == 0) {
            log.warn("No response deleted with ID: {}", responseId);
        } else {
            log.info("Deleted response with ID: {}", responseId);
        }
    }

    public List<Response> getRespondedApplicants(int vacancyId) {
        if (vacancyId <= 0) {
            log.error("Invalid vacancy ID: {}", vacancyId);
            throw new IllegalArgumentException("ID вакансии должен быть положительным числом");
        }

        String sql = "SELECT ra.*, u.name, u.email, u.phone_number, r.name as resume_name " +
                "FROM responded_applicants ra " +
                "JOIN resumes r ON ra.resume_id = r.id " +
                "JOIN users u ON r.applicant_id = u.id " +
                "WHERE ra.vacancy_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Response response = new Response();
            response.setId(rs.getInt("id"));
            response.setResumeId(rs.getInt("resume_id"));
            response.setVacancyId(rs.getInt("vacancy_id"));
            response.setConfirmation(rs.getBoolean("confirmation"));
            return response;
        }, vacancyId);
    }
}