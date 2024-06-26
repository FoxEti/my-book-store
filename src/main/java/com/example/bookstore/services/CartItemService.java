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
    private final BookService bookService;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository, CartRepository cartRepository, BookService bookServise){
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bookService = bookServise;
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

    public void addOrUpdateCartItem(Users user, Long bookId, int quantity) {
        Book book = bookService.getBookById(bookId);
        Cart cart = getCartByUser(user); // Implement this method to get the cart by user
        CartItem existingCartItem = cartItemRepository.findByCartAndBook(cart, book);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setCart(cart);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem);
        }
    }

    // Method to get the cart by user, implement according to your logic
    private Cart getCartByUser(Users user) {
        // Fetch the cart for the user
        return cartRepository.findByUser(user);
    }
    public void deleteCartItemByBookId(Long bookId) {
        cartItemRepository.deleteByBookId(bookId);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteCartItem(cartItemId);
    }
}
