package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Education;
import lombok.RequiredArgsConstructor;
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

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateEducation(Education education) {
        String sql = "UPDATE education_info SET institution = ?, program = ?, start_date = ?, " +
                "end_date = ?, degree = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                education.getInstitution(),
                education.getProgram(),
                Date.valueOf(education.getStartDate()),
                Date.valueOf(education.getEndDate()),
                education.getDegree(),
                education.getId());
    }

    public void deleteEducation(int id) {
        String sql = "DELETE FROM education_info WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}