package com.example.bookstore.services;


import com.example.bookstore.models.Book;
import com.example.bookstore.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addNewBook(Book book) {
        bookRepository.save(book);
        System.out.println(book);
    }

    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchByTitleOrAuthor("%" + keyword.toLowerCase() + "%");
    }

    public void updateBook(Book updatedBook) {
        bookRepository.save(updatedBook);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


}