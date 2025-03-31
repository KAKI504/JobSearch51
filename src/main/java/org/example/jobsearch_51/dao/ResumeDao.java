package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.exceptions.ResumeNotFoundException;
import org.example.jobsearch_51.model.ContactInfo;
import org.example.jobsearch_51.model.Education;
import org.example.jobsearch_51.model.Resume;
import org.example.jobsearch_51.model.WorkExperience;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final EducationDao educationDao;
    private final WorkExperienceDao workExperienceDao;
    private final ContactInfoDao contactInfoDao;
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
            log.error("Resume not found with id: {}", id, e);
            return Optional.empty();
        }
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

    @Transactional
    public int createResumeWithDetails(Resume resume, List<Education> educations,
                                       List<WorkExperience> workExperiences,
                                       List<ContactInfo> contactInfos) {
        if (resume.getApplicantId() <= 0) {
            log.error("Invalid applicant ID: {}", resume.getApplicantId());
            throw new IllegalArgumentException("ID соискателя должен быть положительным числом");
        }

        if (resume.getCategoryId() <= 0) {
            log.error("Invalid category ID: {}", resume.getCategoryId());
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        int resumeId = createResume(resume);
        log.info("Created resume with ID: {}", resumeId);

        if (educations != null) {
            for (Education education : educations) {
                if (education.getResumeId() <= 0) {
                    education.setResumeId(resumeId);
                }
                educationDao.createEducation(education);
                log.info("Added education record to resume {}", resumeId);
            }
        }

        if (workExperiences != null) {
            for (WorkExperience experience : workExperiences) {
                if (experience.getResumeId() <= 0) {
                    experience.setResumeId(resumeId);
                }
                workExperienceDao.createWorkExperience(experience);
                log.info("Added work experience record to resume {}", resumeId);
            }
        }

        if (contactInfos != null) {
            for (ContactInfo contact : contactInfos) {
                if (contact.getResumeId() <= 0) {
                    contact.setResumeId(resumeId);
                }
                contactInfoDao.createContactInfo(contact);
                log.info("Added contact info record to resume {}", resumeId);
            }
        }

        log.info("Successfully created resume with ID: {} including related entities", resumeId);
        return resumeId;
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
        int resumeId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created resume with ID: {}", resumeId);
        return resumeId;
    }

    @Transactional
    public void updateResumeWithDetails(Resume resume, List<Education> educations,
                                        List<WorkExperience> workExperiences,
                                        List<ContactInfo> contactInfos) {
        if (resume.getId() <= 0) {
            log.error("Invalid resume ID: {}", resume.getId());
            throw new IllegalArgumentException("ID резюме должен быть положительным числом");
        }

        if (!resumeExists(resume.getId())) {
            log.error("Resume not found with ID: {}", resume.getId());
            throw new ResumeNotFoundException(resume.getId());
        }

        updateResume(resume);
        log.info("Updated resume with ID: {}", resume.getId());

        if (educations != null) {
            educationDao.deleteEducationsByResumeId(resume.getId());
            log.info("Deleted existing education records for resume ID: {}", resume.getId());

            for (Education education : educations) {
                education.setResumeId(resume.getId());
                educationDao.createEducation(education);
            }
            log.info("Added new education records for resume ID: {}", resume.getId());
        }

        if (workExperiences != null) {
            workExperienceDao.deleteWorkExperiencesByResumeId(resume.getId());
            log.info("Deleted existing work experience records for resume ID: {}", resume.getId());

            for (WorkExperience experience : workExperiences) {
                experience.setResumeId(resume.getId());
                workExperienceDao.createWorkExperience(experience);
            }
            log.info("Added new work experience records for resume ID: {}", resume.getId());
        }

        if (contactInfos != null) {
            contactInfoDao.deleteContactInfoByResumeId(resume.getId());
            log.info("Deleted existing contact info records for resume ID: {}", resume.getId());

            for (ContactInfo contact : contactInfos) {
                contact.setResumeId(resume.getId());
                contactInfoDao.createContactInfo(contact);
            }
            log.info("Added new contact info records for resume ID: {}", resume.getId());
        }

        log.info("Successfully updated resume with ID: {} including related entities", resume.getId());
    }

    public void updateResume(Resume resume) {
        String sql = "UPDATE resumes SET name = ?, category_id = ?, salary = ?, is_active = ?, update_time = ? WHERE id = ?";
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        int rowsUpdated = jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                currentTime,
                resume.getId());

        if (rowsUpdated == 0) {
            log.warn("No resume updated with ID: {}", resume.getId());
        } else {
            log.info("Updated resume with ID: {}", resume.getId());
        }
    }

    public void updateResumeStatus(int resumeId, boolean isActive) {
        String sql = "UPDATE resumes SET is_active = ?, update_time = ? WHERE id = ?";
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        int rowsUpdated = jdbcTemplate.update(sql, isActive, currentTime, resumeId);

        if (rowsUpdated == 0) {
            log.warn("No resume status updated with ID: {}", resumeId);
        } else {
            log.info("Updated resume status: {}, isActive: {}", resumeId, isActive);
        }
    }

    @Transactional
    public void deleteResume(int resumeId) {
        if (!resumeExists(resumeId)) {
            log.error("Cannot delete non-existent resume with ID: {}", resumeId);
            throw new ResumeNotFoundException(resumeId);
        }

        contactInfoDao.deleteContactInfoByResumeId(resumeId);
        log.info("Deleted contact info for resume ID: {}", resumeId);

        educationDao.deleteEducationsByResumeId(resumeId);
        log.info("Deleted education records for resume ID: {}", resumeId);

        workExperienceDao.deleteWorkExperiencesByResumeId(resumeId);
        log.info("Deleted work experience records for resume ID: {}", resumeId);

        String sql = "DELETE FROM resumes WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, resumeId);

        if (rowsDeleted == 0) {
            log.warn("No resume deleted with ID: {}", resumeId);
        } else {
            log.info("Deleted resume with ID: {} and all related entities", resumeId);
        }
    }

    public boolean resumeExists(int resumeId) {
        String sql = "SELECT COUNT(*) FROM resumes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, resumeId);
        return count != null && count > 0;
    }
}