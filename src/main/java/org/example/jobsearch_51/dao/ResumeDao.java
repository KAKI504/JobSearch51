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

    /**
     * Создание резюме со всеми связанными данными (опыт работы, образование и контактная информация)
     * @param resume основные данные резюме
     * @param educations список образования
     * @param workExperiences список опыта работы
     * @param contactInfos список контактной информации
     * @return ID созданного резюме
     */
    @Transactional
    public int createResumeWithDetails(Resume resume, List<Education> educations,
                                       List<WorkExperience> workExperiences,
                                       List<ContactInfo> contactInfos) {
        int resumeId = createResume(resume);

        // Установка ID резюме для связанных сущностей
        if (educations != null) {
            educations.forEach(education -> {
                education.setResumeId(resumeId);
                educationDao.createEducation(education);
            });
        }

        if (workExperiences != null) {
            workExperiences.forEach(experience -> {
                experience.setResumeId(resumeId);
                workExperienceDao.createWorkExperience(experience);
            });
        }

        if (contactInfos != null) {
            contactInfos.forEach(contact -> {
                contact.setResumeId(resumeId);
                contactInfoDao.createContactInfo(contact);
            });
        }

        log.info("Created resume with ID: {} including related entities", resumeId);
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
        if (!resumeExists(resume.getId())) {
            throw new ResumeNotFoundException(resume.getId());
        }

        updateResume(resume);

        if (educations != null) {
            educationDao.deleteEducationsByResumeId(resume.getId());

            educations.forEach(education -> {
                education.setResumeId(resume.getId());
                educationDao.createEducation(education);
            });
        }

        if (workExperiences != null) {
            workExperienceDao.deleteWorkExperiencesByResumeId(resume.getId());

            workExperiences.forEach(experience -> {
                experience.setResumeId(resume.getId());
                workExperienceDao.createWorkExperience(experience);
            });
        }

        if (contactInfos != null) {
            contactInfoDao.deleteContactInfoByResumeId(resume.getId());

            contactInfos.forEach(contact -> {
                contact.setResumeId(resume.getId());
                contactInfoDao.createContactInfo(contact);
            });
        }

        log.info("Updated resume with ID: {} including related entities", resume.getId());
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
        log.info("Updated resume with ID: {}", resume.getId());
    }

    public void updateResumeStatus(int resumeId, boolean isActive) {
        String sql = "UPDATE resumes SET is_active = ?, update_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, isActive, Timestamp.valueOf(LocalDateTime.now()), resumeId);
        log.info("Updated resume status: {}, isActive: {}", resumeId, isActive);
    }

    @Transactional
    public void deleteResume(int resumeId) {
        if (!resumeExists(resumeId)) {
            throw new ResumeNotFoundException(resumeId);
        }

        contactInfoDao.deleteContactInfoByResumeId(resumeId);
        educationDao.deleteEducationsByResumeId(resumeId);
        workExperienceDao.deleteWorkExperiencesByResumeId(resumeId);

        String sql = "DELETE FROM resumes WHERE id = ?";
        jdbcTemplate.update(sql, resumeId);
        log.info("Deleted resume with ID: {} and all related entities", resumeId);
    }

    public boolean resumeExists(int resumeId) {
        String sql = "SELECT COUNT(*) FROM resumes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, resumeId);
        return count != null && count > 0;
    }
}