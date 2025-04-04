package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.dao.CategoryDao;
import org.example.jobsearch_51.model.Category;
import org.example.jobsearch_51.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID категории должен быть положительным числом");
        }

        return categoryDao.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getCategoriesByParent(int parentId) {
        if (parentId < 0) {
            throw new IllegalArgumentException("ID родительской категории не может быть отрицательным");
        }

        return categoryDao.getCategoriesByParentId(parentId);
    }
}