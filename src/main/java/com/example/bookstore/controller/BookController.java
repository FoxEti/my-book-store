package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CartItemService;
import com.example.bookstore.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;
    private final CartItemService cartItemService;
    public BookController(BookService bookService, CartItemService cartItemService) {
        this.bookService = bookService;
        this.cartItemService = cartItemService;
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

    @PostMapping("/admin/book")
    public String addBook(
            @RequestParam Long id,
            @RequestParam String imageUrl,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String price,
            @RequestParam String detailsBook,
            @RequestParam String category,
            @RequestParam Integer stockBook
    ) {
        if (id <= 0) {
            // Create a new book object and save it to the database
            Book newBook = new Book(imageUrl, title, author, Double.parseDouble(price), detailsBook, category, stockBook);
            bookService.addBook(newBook);
        } else {
            Book existingBook = new Book(id, imageUrl, title, author, Double.parseDouble(price), detailsBook, category, stockBook);
            bookService.updateBook(id, existingBook);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/removeBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            cartItemService.deleteCartItemByBookId(id);
            bookService.deleteBookById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detailsBook/{bookId}")
    @ResponseBody
    public ResponseEntity<Book> getBookDetails(@PathVariable Long bookId) {
        try {
            Book book = bookService.getBookById(bookId);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/editBook/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book, Model model) {
        bookService.updateBook(id, book);
        return "redirect:/admin/books"; // Redirect to the book list page after updating
    }
}