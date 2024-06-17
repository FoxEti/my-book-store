package com.example.bookstore.repository;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);


    CartItem findByCartAndBook(Cart cart, Book book);
}
