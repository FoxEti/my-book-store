package com.example.bookstore.controller;

import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.CartItem;
import com.example.bookstore.models.Users;
import com.example.bookstore.services.CartItemService;
import com.example.bookstore.services.CartService;
import com.example.bookstore.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartItemService cartItemService;
    private final UsersService userService;
    private final BookController bookService;
    private final CartService cartService;

    public CartController(CartItemService cartItemService, UsersService userService, CartService cartService, BookController bookService, CartService cartService1) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.bookService = bookService;
        this.cartService = cartService1;
    }

    @GetMapping("/cart")
    public String cartPage(Model model) {
        Users currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            model.addAttribute("error", "No user is currently logged in.");
            return "error";
        }

        List<CartItem> cartItems = cartItemService.getCartItemsForUser(currentUser);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("currentUser", currentUser);

        double totalPrice = 0;
        Map<Long, Integer> bookQuantities = new HashMap<>();
        for (CartItem cartItem : cartItems) {

            Book book = bookService.getBookById(cartItem.getBook().getId());
            cartItem.setBook(book); // Assuming you have a setter for Book in CartItem
            totalPrice += book.getPrice() * cartItem.getQuantity();

            bookQuantities.put(cartItem.getBook().getId(),
                    bookQuantities.getOrDefault(cartItem.getBook().getId(), 0) + cartItem.getQuantity());
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("bookQuantities", bookQuantities);

        return "cart"; // The name of your HTML template file
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Long> payload) {
        Long bookId = payload.get("bookId");
        Book currentBook = bookService.getBookById(bookId);
        if(currentBook.getStockBook()<=0){
            return ResponseEntity.badRequest().body("Book is out of stock.");

        }
        Users currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("User not logged in...");
        }
        Cart cart = cartService.getOrCreateCartByUserId(currentUser.getId());

        cartItemService.addOrUpdateCartItem(currentUser, bookId, 1,cart);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(@RequestBody Map<String, Long> payload) {
        Long cartItemId = payload.get("cartItemId");
        Users currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("User not logged in...");
        }

        try {
            cartItemService.deleteCartItem(cartItemId);
            return ResponseEntity.ok().build(); // Successful deletion
        } catch (Exception e) {
            logger.error("Error deleting cart item: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting cart item.");
        }
    }


}