package com.example.bookstore.services;


import com.example.bookstore.models.*;
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

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final BookService bookService;
    private final OrderService orderService;
    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, BookService bookServise, OrderService orderService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.bookService = bookServise;
        this.orderService = orderService;
    }

    public List<CartItem> getCartItemsForUser(Users currentUser) {
        Cart cart = cartRepository.findByUserAndCartStatus(currentUser.getId(), Cart.CartStatus.IN_PROCESS);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cartItemRepository.findByCart(cart.getId());
    }

    public void addOrUpdateCartItem(Users user, Long bookId, int quantity,Cart cart) {
        Book book = bookService.getBookById(bookId);

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

    // Method to get the cart by user
    private Cart getCartByUser(Users user) {
        return cartRepository.findByUserAndCartStatus(user.getId(), Cart.CartStatus.IN_PROCESS);
    }

    public void deleteCartItemByBookId(Long bookId) {
        cartItemRepository.deleteByBookId(bookId);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteCartItem(cartItemId);
    }

    public List<CartItem> getCartItemsByCartId(Cart cart) {
        return cartItemRepository.findByCart(cart.getId());
    }

    public List<CartItem> getCurrentOrderedBooks(Cart cart) {
       return cartItemRepository.findByCart(cart.getId());
    }
}
