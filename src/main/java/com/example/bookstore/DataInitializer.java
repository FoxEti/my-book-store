package com.example.bookstore;

import com.example.bookstore.models.Users;

import com.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

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
}