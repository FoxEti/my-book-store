package com.example.bookstore.repository;



import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.cartStatus = :cartStatus")
    Optional<Cart> findByUserIdAndCartStatus(@Param("userId") Long userId, @Param("cartStatus") Cart.CartStatus cartStatus);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.cartStatus = :cartStatus")
    Cart findByUserAndCartStatus(@Param("userId") Long userId,@Param("cartStatus") Cart.CartStatus cartStatus);

    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Optional<Cart> findByCartId(@Param("cartId") Long cartId);

    @Query("SELECT MAX(c.id) FROM Cart c WHERE c.user.id = :userId")
    Long findMaxIdByUserId(@Param("userId") Long userId);
}
