package com.example.bookstore.controller;

import com.example.bookstore.models.Users;
import com.example.bookstore.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UsersService userService;

    public RegistrationController(UsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        logger.info("Accessing registration page");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {
        logger.info("Attempting to register user: {}", username);
        if (userService.userExists(username)) {
            logger.error("Username already exists: {}", username);
            model.addAttribute("error", "Username already exists.");
            return "register";
        }

        Users user = new Users(username, password, email);
        user.setRole(Users.Role.USER);
        userService.saveUser(user);
        logger.info("User registered successfully: {}", username);

        return "redirect:/login";
    }
}