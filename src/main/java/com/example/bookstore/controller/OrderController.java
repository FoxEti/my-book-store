package com.example.bookstore.controller;

import com.example.bookstore.models.*;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final UsersService usersService;
    private final CartService cartService;
    private final BookService bookService;
    private final CartItemService cartItemService;
    public OrderController(OrderService orderService, UsersService usersService, CartService cartService, BookService bookService, CartItemService cartItemService) {
        this.orderService = orderService;
        this.usersService = usersService;
        this.cartService = cartService;
        this.bookService = bookService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/order")
    public String orderPage(@RequestParam Double totalPrice,Model model) {
        // Retrieve the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users currentUser = usersService.findByUsername(username);

        // Pass the user ID and totalPrice to the model
        model.addAttribute("userId", currentUser.getId());
        model.addAttribute("totalPrice", totalPrice);
        return "order"; // Return the view name (order.html)
    }

    @PostMapping("/processPayment")
    public String processPayment(@RequestParam String address,
                                 @RequestParam Integer phoneNumber,
                                 @RequestParam String shippingType,
                                 @RequestParam Double totalPrice,
                                 @RequestParam Long userId) {

        Cart currntCart = cartService.getCartByUserId(userId);
        currntCart.setCartStatus(Cart.CartStatus.COMPLETED);
        Order order = new Order();
        //insert values into the DB
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setShippingType(shippingType);
        order.setOrderStatus(Order.OrderStatus.COMPLETED); // Set order status to NEW
        order.setOrderDate(new Date()); // Set current date/time
        order.setTotalPrice(totalPrice);
        order.setCartId(currntCart.getId());
        orderService.saveOrder(order); // Save order via service

        //Update the amount of book stock according to the user's cart
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(currntCart);
        for (CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            int quantityInCart = cartItem.getQuantity();
            int updatedInventory = book.getStockBook() - quantityInCart;
            //if the book is available for purchase
            if(updatedInventory>=0) {
                book.setStockBook(updatedInventory);
                bookService.saveBook(book);
            }

        }

        //Update the user with a new cart
        Cart newCart = new Cart();
        newCart.setCartStatus(Cart.CartStatus.IN_PROCESS);
        Users currentUser = usersService.findUserById(userId);
        newCart.setUser(currentUser);
        cartService.saveCart(newCart);
        return "redirect:/";
    }


}
