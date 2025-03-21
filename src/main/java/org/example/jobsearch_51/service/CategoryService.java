package org.example.jobsearch_51.service;

import org.example.jobsearch_51.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    List<Category> getCategoriesByParent(int parentId);
}