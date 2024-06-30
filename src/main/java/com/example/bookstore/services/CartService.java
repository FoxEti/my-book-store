package com.example.bookstore.services;

import com.example.bookstore.models.Cart;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Cart getOrCreateCartByUserId(Long userId) {
        return cartRepository.findByUserIdAndCartStatus(userId,Cart.CartStatus.IN_PROCESS).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setCartStatus(Cart.CartStatus.IN_PROCESS);
            cart.setUser(userRepository.findById(userId).orElse(null));
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getOrCreateCartByUserId(userId);
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        cartRepository.save(cart);
    }

    public Optional<Cart> findCartById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    public void saveCart(Cart newCart) {
        cartRepository.save(newCart);
    }


    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserIdAndCartStatus(userId,Cart.CartStatus.IN_PROCESS);
    }

    public Optional<Cart> getCartRecent(Long userId) {
        Long maxId = cartRepository.findMaxIdByUserId(userId);
        if (maxId != null) {
            return cartRepository.findByCartId(maxId);
        } else {
            return Optional.empty();
        }
    }
}