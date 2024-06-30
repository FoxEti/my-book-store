package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.CartItem;
import com.example.bookstore.models.Category;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Book> searchBooks(String keyword, Double minPrice, Double maxPrice,String category) {
        if (keyword != null && !keyword.isEmpty()) {
            return bookRepository.searchBooks(keyword.toLowerCase());
        } else if (minPrice != null && maxPrice != null) {
            return bookRepository.findByPriceBetween(minPrice, maxPrice);
        } else if (category != null && !category.isEmpty()) {
            Long categoryId;
            switch (category.toLowerCase()) {
                case "children":
                    categoryId = 1L;
                    break;
                case "youth":
                    categoryId = 2L;
                    break;
                case "adults":
                    categoryId = 3L;
                    break;
                default:
                    categoryId = null;
            }
            if (categoryId != null) {
                return bookRepository.findByCategoryId(categoryId);
            }
        }
        return bookRepository.findAll();
    }

    public void saveBook(Book book) {
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

    public List<Book> getBooksByCartItems(List<CartItem> orderedBooks) {
        // Extract book IDs from the orderedBooks list
        List<Long> bookIds = orderedBooks.stream()
                .map(cartItem -> cartItem.getBook().getId())
                .collect(Collectors.toList());

        // Use the book repository to find all books by their IDs
        return bookRepository.findAllById(bookIds);
    }
}
