package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.exceptions.EducationNotFoundException;
import org.example.jobsearch_51.model.Education;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EducationDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Education> getEducationByResumeId(int resumeId) {
        String sql = "SELECT * FROM education_info WHERE resume_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Education education = new Education();
            education.setId(rs.getInt("id"));
            education.setResumeId(rs.getInt("resume_id"));
            education.setInstitution(rs.getString("institution"));
            education.setProgram(rs.getString("program"));
            education.setStartDate(rs.getDate("start_date").toLocalDate());
            education.setEndDate(rs.getDate("end_date").toLocalDate());
            education.setDegree(rs.getString("degree"));
            return education;
        }, resumeId);
    }

    public Optional<Education> getEducationById(int id) {
        String sql = "SELECT * FROM education_info WHERE id = ?";
        try {
            List<Education> educations = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Education education = new Education();
                education.setId(rs.getInt("id"));
                education.setResumeId(rs.getInt("resume_id"));
                education.setInstitution(rs.getString("institution"));
                education.setProgram(rs.getString("program"));
                education.setStartDate(rs.getDate("start_date").toLocalDate());
                education.setEndDate(rs.getDate("end_date").toLocalDate());
                education.setDegree(rs.getString("degree"));
                return education;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(educations));
        } catch (EmptyResultDataAccessException e) {
            log.error("Education not found with id: {}", id, e);
            return Optional.empty();
        }
    }

    public int createEducation(Education education) {
        String sql = "INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, education.getResumeId());
                    ps.setString(2, education.getInstitution());
                    ps.setString(3, education.getProgram());
                    ps.setDate(4, Date.valueOf(education.getStartDate()));
                    ps.setDate(5, Date.valueOf(education.getEndDate()));
                    ps.setString(6, education.getDegree());
                    return ps;
                }, keyHolder
        );

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created education record with ID: {}", id);
        return id;
    }

    public void updateEducation(Education education) {
        String sql = "UPDATE education_info SET institution = ?, program = ?, start_date = ?, " +
                "end_date = ?, degree = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                education.getInstitution(),
                education.getProgram(),
                Date.valueOf(education.getStartDate()),
                Date.valueOf(education.getEndDate()),
                education.getDegree(),
                education.getId());

        if (rowsAffected == 0) {
            throw new EducationNotFoundException(education.getId());
        }

        log.info("Updated education record with ID: {}", education.getId());
    }

    public void deleteEducation(int id) {
        String sql = "DELETE FROM education_info WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            throw new EducationNotFoundException(id);
        }

        log.info("Deleted education record with ID: {}", id);
    }

    public void deleteEducationsByResumeId(int resumeId) {
        String sql = "DELETE FROM education_info WHERE resume_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, resumeId);
        log.info("Deleted {} education records for resume ID: {}", rowsAffected, resumeId);
    }

    public boolean educationExists(int educationId) {
        String sql = "SELECT COUNT(*) FROM education_info WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, educationId);
        return count != null && count > 0;
    }
}