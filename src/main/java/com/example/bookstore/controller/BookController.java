package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                          @RequestParam Integer stockBook
                          ) {
        // Create a new book object and save it to the database
        Book newBook = new Book(imageUrl, title, author, Double.parseDouble(price), detailsBook, category,stockBook);
        bookService.addBook(newBook);
        return "redirect:/admin";
    }

    @DeleteMapping("/removeBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/editBook/{id}")
    public ResponseEntity<Void> editBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        try {
            Book existingBook = bookService.getBookById(id);
            if (existingBook != null) {
                existingBook.setImageUrl(updatedBook.getImageUrl());
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setPrice(updatedBook.getPrice());
                existingBook.setDetails(updatedBook.getDetails());
                existingBook.setCategory(updatedBook.getCategory());
                existingBook.setStockBook(updatedBook.getStockBook());
                //existingBook.setStatus(updatedBook.getStatus());
                bookService.saveEdits(existingBook);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}