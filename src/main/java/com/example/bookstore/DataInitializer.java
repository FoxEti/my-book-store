package com.example.bookstore;

import com.example.bookstore.models.Category;
import com.example.bookstore.models.Users;

import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;
    private final CategoryService categoryService;

    public DataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Bean
    CommandLineRunner init() {
        return args -> {
            if (userRepository.findByUserName("user") == null) {
                Users user = new Users("user", "password", "user@example.com");
                user.setRole(Users.Role.USER);
                userRepository.save(user);
            }
        };
    }

    /*@Override
    public void run(String... args) throws Exception {
        categoryService.initializeCategories();
    }*/
}