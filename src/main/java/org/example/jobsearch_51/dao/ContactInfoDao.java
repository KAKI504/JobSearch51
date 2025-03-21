package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.ContactInfo;
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
            contactInfo.setValue(rs.getString("value"));
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
                contactInfo.setValue(rs.getString("value"));
                return contactInfo;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(contacts));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int createContactInfo(ContactInfo contactInfo) {
        String sql = "INSERT INTO contacts_info (type_id, resume_id, value) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, contactInfo.getTypeId());
                    ps.setInt(2, contactInfo.getResumeId());
                    ps.setString(3, contactInfo.getValue());
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateContactInfo(ContactInfo contactInfo) {
        String sql = "UPDATE contacts_info SET type_id = ?, value = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                contactInfo.getTypeId(),
                contactInfo.getValue(),
                contactInfo.getId());
    }

    public void deleteContactInfo(int id) {
        String sql = "DELETE FROM contacts_info WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}