package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Category;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.CartItemService;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;
    private final CartItemService cartItemService;
    private final CategoryService categoryService;
    public BookController(BookService bookService, CartItemService cartItemService, CategoryService categoryService) {
        this.bookService = bookService;
        this.cartItemService = cartItemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/bookDetails/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookDetails"; // The name of your HTML template for book details
    }

    public Book getBookById(Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping("/admin/book")
    public String addBook(
            @RequestParam Long id,
            @RequestParam String imageUrl,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Double price,
            @RequestParam String detailsBook,
            @RequestParam Long categoryId,
            @RequestParam Integer stockBook
    ) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {

            return "redirect:/admin"; // Redirect to admin page or show error message
        }
        if (id <= 0) {
            // Create a new book object and save it to the database
            Book newBook = new Book(imageUrl, title, author, price, detailsBook, category, stockBook);
            bookService.saveBook(newBook);
        } else {
            Book existingBook = new Book(id, imageUrl, title, author, price, detailsBook, category, stockBook);
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