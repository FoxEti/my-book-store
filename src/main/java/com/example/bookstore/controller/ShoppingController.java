package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShoppingController {

    private final BookService bookService;

    public ShoppingController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shopping")
    public String shoppingPage(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "minPrice", required = false) Double minPrice,
                               @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                               @RequestParam(value = "category",required = false) String category) {
        List<Book> books = bookService.searchBooks(keyword, minPrice, maxPrice,category);
        model.addAttribute("books", books);
        return "shopping";
    }

}
