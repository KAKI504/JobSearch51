package org.example.jobsearch_51.dao;

import org.example.jobsearch_51.model.Category;
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
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setParentId(rs.getObject("parent_id", Integer.class));
            return category;
        });
    }

    public List<Category> getCategoriesByParentId(Integer parentId) {
        String sql = "SELECT * FROM categories WHERE parent_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setParentId(rs.getObject("parent_id", Integer.class));
            return category;
        }, parentId);
    }

    public Optional<Category> getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try {
            List<Category> categories = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setParentId(rs.getObject("parent_id", Integer.class));
                return category;
            }, id);
            return Optional.ofNullable(DataAccessUtils.singleResult(categories));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int createCategory(Category category) {
        String sql = "INSERT INTO categories (name, parent_id) VALUES (?, ?)";

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1, category.getName());
                    if (category.getParentId() != null) {
                        ps.setInt(2, category.getParentId());
                    } else {
                        ps.setNull(2, java.sql.Types.INTEGER);
                    }
                    return ps;
                }, keyHolder
        );

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, parent_id = ? WHERE id = ?";
        if (category.getParentId() != null) {
            jdbcTemplate.update(sql, category.getName(), category.getParentId(), category.getId());
        } else {
            jdbcTemplate.update(sql, category.getName(), null, category.getId());
        }
    }

    public void deleteCategory(int categoryId) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, categoryId);
    }
}