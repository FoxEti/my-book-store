package com.example.bookstore.repository;

import com.example.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE lower(b.title) LIKE %:keyword% OR lower(b.author) LIKE %:keyword%")
    List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);


    Book findBookById(Long bookId);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:query% OR b.author LIKE %:query% OR b.category LIKE %:query%")
    List<Book> findBySearchField(@Param("query") String query);

    List<Book> findByTitleContainingOrAuthorContainingOrCategoryContaining(String title, String author, String category);
}


