package com.example.bookstore.controller;
import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class adminController {

    private final BookService bookService;

    public adminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Book> books;
        if (keyword != null && !keyword.isEmpty()) {
            books = bookService.searchBooks(keyword);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "admin";
    }
}
