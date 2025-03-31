package org.example.jobsearch_51.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.Review;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Review> getReviewsByVacancyId(int vacancyId) {
        String sql = "SELECT * FROM reviews WHERE vacancy_id = ? ORDER BY created_date DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Review review = new Review();
            review.setId(rs.getInt("id"));
            review.setVacancyId(rs.getInt("vacancy_id"));
            review.setUserId(rs.getInt("user_id"));
            review.setContent(rs.getString("content"));
            review.setRating(rs.getInt("rating"));
            review.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            return review;
        }, vacancyId);
    }

    public Optional<Review> getReviewById(int id) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try {
            List<Review> reviews = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setVacancyId(rs.getInt("vacancy_id"));
                review.setUserId(rs.getInt("user_id"));
                review.setContent(rs.getString("content"));
                review.setRating(rs.getInt("rating"));
                review.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                return review;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(reviews));
        } catch (EmptyResultDataAccessException e) {
            log.error("Review not found with id: {}", id, e);
            return Optional.empty();
        }
    }

    public int createReview(Review review) {
        String sql = "INSERT INTO reviews (vacancy_id, user_id, content, rating, created_date) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setInt(1, review.getVacancyId());
                    ps.setInt(2, review.getUserId());
                    ps.setString(3, review.getContent());
                    ps.setInt(4, review.getRating());
                    ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                }, keyHolder
        );

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        log.info("Created review with ID: {}", id);
        return id;
    }

    public void deleteReview(int id) {
        String sql = "DELETE FROM reviews WHERE id = ?";
        int rowsDeleted = jdbcTemplate.update(sql, id);

        if (rowsDeleted == 0) {
            log.warn("No review found to delete with ID: {}", id);
        } else {
            log.info("Deleted review with ID: {}", id);
        }
    }
}