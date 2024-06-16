package com.example.bookstore.repository;



import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);

    Cart findByUser(Users user);
}
