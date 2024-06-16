package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookDetails"; // Make sure you have a corresponding Thymeleaf template for book details
    }
}