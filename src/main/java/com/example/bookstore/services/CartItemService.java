package com.example.bookstore.services;


import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.CartItem;
import com.example.bookstore.models.Users;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository, CartRepository cartRepository, BookRepository bookRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    private Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userRepository.findByUserName(userName);
    }

    public List<CartItem> getCartItemsForUser(Users currentUser) {
        Cart cart = cartRepository.findByUser(currentUser);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cartItemRepository.findByCart(cart);
    }

    @Transactional
    public void addBookToCart(Users user, Long bookId) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
    }
}
