package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.WorkExperience;
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
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<WorkExperience> getWorkExperienceByResumeId(int resumeId) {
        String sql = "SELECT * FROM work_experience_info WHERE resume_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WorkExperience workExperience = new WorkExperience();
            workExperience.setId(rs.getInt("id"));
            workExperience.setResumeId(rs.getInt("resume_id"));
            workExperience.setYears(rs.getInt("years"));
            workExperience.setCompanyName(rs.getString("company_name"));
            workExperience.setPosition(rs.getString("position"));
            workExperience.setResponsibilities(rs.getString("responsibilities"));
            return workExperience;
        }, resumeId);
    }

    public Optional<WorkExperience> getWorkExperienceById(int id) {
        String sql = "SELECT * FROM work_experience_info WHERE id = ?";
        try {
            List<WorkExperience> experiences = jdbcTemplate.query(sql, (rs, rowNum) -> {
                WorkExperience workExperience = new WorkExperience();
                workExperience.setId(rs.getInt("id"));
                workExperience.setResumeId(rs.getInt("resume_id"));
                workExperience.setYears(rs.getInt("years"));
                workExperience.setCompanyName(rs.getString("company_name"));
                workExperience.setPosition(rs.getString("position"));
                workExperience.setResponsibilities(rs.getString("responsibilities"));
                return workExperience;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(experiences));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int createWorkExperience(WorkExperience workExperience) {
        String sql = "INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, workExperience.getResumeId());
                    ps.setInt(2, workExperience.getYears());
                    ps.setString(3, workExperience.getCompanyName());
                    ps.setString(4, workExperience.getPosition());
                    ps.setString(5, workExperience.getResponsibilities());
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateWorkExperience(WorkExperience workExperience) {
        String sql = "UPDATE work_experience_info SET years = ?, company_name = ?, position = ?, " +
                "responsibilities = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                workExperience.getYears(),
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getResponsibilities(),
                workExperience.getId());
    }

    public void deleteWorkExperience(int id) {
        String sql = "DELETE FROM work_experience_info WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}