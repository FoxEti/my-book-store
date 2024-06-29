package com.example.bookstore.services;

import com.example.bookstore.models.Category;
import com.example.bookstore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findCategoryByName(categoryName);
    }

    public Category getOrCreateCategoryByName(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            // Category doesn't exist, create a new one
            category = new Category();
            category.setCategoryName(categoryName);
            category = categoryRepository.save(category);
        }
        return category;
    }

    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.orElse(null); // Return null if category is not found
    }
}
