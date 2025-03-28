package org.example.jobsearch_51.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jobsearch_51.model.Category;
import org.example.jobsearch_51.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        log.info("Requesting all categories");
        return categoryService.getAllCategories();
    }

    @GetMapping("{id}")
    public Category getCategoryById(
            @PathVariable @Min(value = 1, message = "ID категории должен быть положительным числом") int id) {
        log.info("Requesting category with id: {}", id);
        return categoryService.getCategoryById(id);
    }

    @GetMapping("parent/{parentId}")
    public List<Category> getCategoriesByParent(
            @PathVariable @Min(value = 0, message = "ID родительской категории не может быть отрицательным") int parentId) {
        log.info("Requesting categories with parent id: {}", parentId);
        return categoryService.getCategoriesByParent(parentId);
    }
}