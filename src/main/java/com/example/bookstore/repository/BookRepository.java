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

    @Query("SELECT b FROM Book b WHERE " +
            "(:keyword IS NULL OR lower(b.title)  LIKE %:keyword% OR lower(b.author) LIKE %:keyword% OR lower(b.category.categoryName) LIKE %:keyword%) ")
    List<Book> searchBooks(@Param("keyword") String keyword);

    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT b FROM Book b WHERE b.category.id = :categoryId")
    List<Book> findByCategoryId(@Param("categoryId") Long categoryId);

    List<Book> findAllById(Iterable<Long> ids);
}


