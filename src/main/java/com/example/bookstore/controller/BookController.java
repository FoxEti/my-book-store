package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/bookDetails/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookDetails"; // The name of your HTML template for book details
    }

    public Book getBookById(Book book) {
        return bookService.getBookById(book.getId());
    }

    @PostMapping("/admin/addBook")
    public String addBook(@RequestParam String imageUrl,
                          @RequestParam String title,
                          @RequestParam String author,
                          @RequestParam String price,
                          @RequestParam String detailsBook,
                          @RequestParam String category,
                          @RequestParam Integer stockBook,
                          @RequestParam String status) {
        // Create a new book object and save it to the database
        Book newBook = new Book(imageUrl, title, author, Double.parseDouble(price), detailsBook, category, stockBook, status);
        bookService.addBook(newBook);
        return "redirect:/admin";
    }
}