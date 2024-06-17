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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartItemService cartItemService;
    private final UsersService userService;
    private final CartService cartService;

    public CartController(CartItemService cartItemService, UsersService userService, CartService cartService) {
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.cartService = cartService;
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

        double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getBook().getPrice() * cartItem.getQuantity())
                .sum();
        model.addAttribute("totalPrice", totalPrice);

        return "cart"; // The name of your HTML template file
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Long> payload) {
        Long bookId = payload.get("bookId");
        Users currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("User not logged in.");
        }
        cartItemService.addBookToCart(currentUser, bookId);
        return ResponseEntity.ok().build();
    }


}