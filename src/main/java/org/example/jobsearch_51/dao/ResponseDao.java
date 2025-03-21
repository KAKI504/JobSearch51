package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Response;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class ResponseDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Response> getResponsesByVacancyId(int vacancyId) {
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
            return Optional.empty();
        }
    }

    public int createResponse(Response response) {
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

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateResponseConfirmation(int responseId, boolean confirmation) {
        String sql = "UPDATE responded_applicants SET confirmation = ? WHERE id = ?";
        jdbcTemplate.update(sql, confirmation, responseId);
    }

    public void deleteResponse(int responseId) {
        String sql = "DELETE FROM responded_applicants WHERE id = ?";
        jdbcTemplate.update(sql, responseId);
    }
}