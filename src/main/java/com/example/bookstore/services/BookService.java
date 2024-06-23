package com.example.bookstore.services;

import com.example.bookstore.models.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingOrAuthorContainingOrCategoryContaining(keyword, keyword, keyword);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public void saveEdits(Book book) {
        Optional<Book> existingBook = bookRepository.findById(book.getId()); // Find book by ID
        if (existingBook.isPresent()) {
            existingBook.get().setImageUrl(book.getImageUrl());  // Update properties
            existingBook.get().setTitle(book.getTitle());
            existingBook.get().setAuthor(book.getAuthor());
            existingBook.get().setStockBook(book.getStockBook());
            existingBook.get().setPrice(book.getPrice());
            existingBook.get().setDetails(book.getDetails());
            existingBook.get().setCategory(book.getCategory());
            existingBook.get().setStatus();

            bookRepository.save(existingBook.get());
        } else {
            System.out.println("error update a details of book" + book.getTitle());
        }
    }
}
