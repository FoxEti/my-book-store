package com.example.bookstore.controller;
import com.example.bookstore.models.Book;
import com.example.bookstore.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class adminController {

    private final BookService bookService;

    public adminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "minPrice", required = false) Double minPrice,
                            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                            @RequestParam(value = "category",required = false) String category) {
        List<Book> books;
        if ((keyword != null && !keyword.isEmpty()) || (minPrice != null && maxPrice != null)) {
            books = bookService.searchBooks(keyword, minPrice, maxPrice,category);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        return "admin";
    }

    @PostMapping("/admin/editBook/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/admin";
    }
}
