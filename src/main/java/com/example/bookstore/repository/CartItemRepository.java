package com.example.bookstore.repository;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);


    CartItem findByCartAndBook(Cart cart, Book book);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.id = :cartItemId")
    void deleteCartItem(@Param("cartItemId") Long cartItemId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.book.id = :bookId")
    void deleteByBookId(@Param ("bookId") Long bookId);
}
