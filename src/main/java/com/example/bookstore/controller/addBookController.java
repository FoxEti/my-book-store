package com.example.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class addBookController {

    @GetMapping("/addBook")
    public String addBook() {
        return "addBook"; // This will look for addBook.html in the templates directory
    }
}
