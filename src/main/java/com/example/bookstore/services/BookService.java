package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Category;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookService(BookRepository bookRepository, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String keyword, Double minPrice, Double maxPrice) {
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.findByTitleContainingOrAuthorContainingOrCategory_CategoryNameContaining(keyword, keyword, keyword);
        } else if (minPrice != null && maxPrice != null) {
            return bookRepository.findByPriceBetween(minPrice, maxPrice);
        } else {
            return bookRepository.findAll();
        }
    }



    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        existingBook.setImageUrl(book.getImageUrl());
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPrice(book.getPrice());
        existingBook.setDetails(book.getDetails());
        existingBook.setCategory(book.getCategory());
        existingBook.setStockBook(book.getStockBook());
        bookRepository.save(existingBook);
    }
}
