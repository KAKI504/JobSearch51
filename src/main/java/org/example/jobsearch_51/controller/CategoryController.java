package org.example.jobsearch_51.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.Category;
import org.example.jobsearch_51.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        log.info("Requesting all categories");
        return categoryService.getAllCategories();
    }

    @GetMapping("{id}")
    public Category getCategoryById(@PathVariable int id) {
        log.info("Requesting category with id: {}", id);
        return categoryService.getCategoryById(id);
    }

    @GetMapping("parent/{parentId}")
    public List<Category> getCategoriesByParent(@PathVariable int parentId) {
        log.info("Requesting categories with parent id: {}", parentId);
        return categoryService.getCategoriesByParent(parentId);
    }
}