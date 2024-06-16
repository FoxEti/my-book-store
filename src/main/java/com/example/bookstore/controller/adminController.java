package com.example.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class adminController {
    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // The name of your HTML template file
    }
}
