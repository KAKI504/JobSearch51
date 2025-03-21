package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MessageDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Message> getMessagesByResponseId(int responseId) {
        String sql = "SELECT * FROM messages WHERE responded_applicants_id = ? ORDER BY timestamp ASC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Message message = new Message();
            message.setId(rs.getInt("id"));
            message.setResponseId(rs.getInt("responded_applicants_id"));
            message.setContent(rs.getString("content"));
            message.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            message.setSenderId(rs.getInt("sender_id"));
            return message;
        }, responseId);
    }

    public int createMessage(Message message) {
        String sql = "INSERT INTO messages (responded_applicants_id, content, sender_id) VALUES (?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, message.getResponseId());
                    ps.setString(2, message.getContent());
                    ps.setInt(3, message.getSenderId());
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}