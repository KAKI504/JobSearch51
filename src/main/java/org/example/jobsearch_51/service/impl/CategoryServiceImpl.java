package org.example.jobsearch_51.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.jobsearch_51.model.Category;
import org.example.jobsearch_51.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>();
    }

    @Override
    public Category getCategoryById(int id) {
        return new Category();
    }

    @Override
    public List<Category> getCategoriesByParent(int parentId) {
        return new ArrayList<>();
    }
}