package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.ContactInfo;
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
public class ContactInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<ContactInfo> getContactInfoByResumeId(int resumeId) {
        String sql = "SELECT * FROM contacts_info WHERE resume_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setId(rs.getInt("id"));
            contactInfo.setTypeId(rs.getInt("type_id"));
            contactInfo.setResumeId(rs.getInt("resume_id"));
            contactInfo.setContactValue(rs.getString("contact_value"));
            return contactInfo;
        }, resumeId);
    }

    public Optional<ContactInfo> getContactInfoById(int id) {
        String sql = "SELECT * FROM contacts_info WHERE id = ?";
        try {
            List<ContactInfo> contacts = jdbcTemplate.query(sql, (rs, rowNum) -> {
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setId(rs.getInt("id"));
                contactInfo.setTypeId(rs.getInt("type_id"));
                contactInfo.setResumeId(rs.getInt("resume_id"));
                contactInfo.setContactValue(rs.getString("contact_value"));
                return contactInfo;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(contacts));
        } catch (EmptyResultDataAccessException e) {
            log.error("Contact info not found with id: {}", id, e);
            return Optional.empty();
        }
    }

    public int createContactInfo(ContactInfo contactInfo) {
        String sql = "INSERT INTO contacts_info (type_id, resume_id, contact_value) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, contactInfo.getTypeId());
                    ps.setInt(2, contactInfo.getResumeId());
                    ps.setString(3, contactInfo.getContactValue());
                    return ps;
                }, keyHolder
        );

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created contact info with ID: {}", id);
        return id;
    }

    public void updateContactInfo(ContactInfo contactInfo) {
        String sql = "UPDATE contacts_info SET type_id = ?, contact_value = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                contactInfo.getTypeId(),
                contactInfo.getContactValue(),
                contactInfo.getId());

        if (rowsAffected == 0) {
            log.warn("No contact info found to update with ID: {}", contactInfo.getId());
        } else {
            log.info("Updated contact info with ID: {}", contactInfo.getId());
        }
    }

    public void deleteContactInfo(int id) {
        String sql = "DELETE FROM contacts_info WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected == 0) {
            log.warn("No contact info found to delete with ID: {}", id);
        } else {
            log.info("Deleted contact info with ID: {}", id);
        }
    }

    public void deleteContactInfoByResumeId(int resumeId) {
        String sql = "DELETE FROM contacts_info WHERE resume_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, resumeId);
        log.info("Deleted {} contact info records for resume ID: {}", rowsAffected, resumeId);
    }

    public boolean contactInfoExists(int contactInfoId) {
        String sql = "SELECT COUNT(*) FROM contacts_info WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, contactInfoId);
        return count != null && count > 0;
    }
}