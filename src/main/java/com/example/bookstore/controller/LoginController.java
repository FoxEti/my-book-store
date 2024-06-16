package com.example.bookstore.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }
}
