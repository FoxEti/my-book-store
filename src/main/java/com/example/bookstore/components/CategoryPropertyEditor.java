package com.example.bookstore.components;

import com.example.bookstore.models.Category;
import com.example.bookstore.services.CategoryService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CategoryPropertyEditor extends PropertyEditorSupport {

    private final CategoryService categoryService;

    public CategoryPropertyEditor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Category category = categoryService.getCategoryByName(text);
        setValue(category);
    }
}
