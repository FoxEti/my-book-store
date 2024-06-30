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
import java.util.Optional;

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

        // Check if the user's cart is empty
        List<CartItem> cartItems = cartItemService.getCartItemsForUser(currentUser);
        if (cartItems.isEmpty()) {
            model.addAttribute("errorMessage", "Your cart is empty.");
            return "cart"; // Return to cart.html with an error message
        }

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

        Optional<Cart> optionalCart = cartService.getCartByUserId(userId);
        if(!optionalCart.isPresent())
            return "";

        Cart currentCart = optionalCart.get();
        currentCart.setCartStatus(Cart.CartStatus.COMPLETED);
        Order order = new Order();
        //insert values into the DB
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setShippingType(shippingType);
        order.setOrderStatus(Order.OrderStatus.COMPLETED); // Set order status to NEW
        order.setOrderDate(new Date()); // Set current date/time
        order.setTotalPrice(totalPrice);
        order.setCartId(currentCart.getId());
        orderService.saveOrder(order); // Save order via service

        //Update the amount of book stock according to the user's cart
        List<CartItem> cartItems = cartItemService.getCartItemsByCartId(currentCart);
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

        return "redirect:/orderCompleted";
    }

    @GetMapping("/orderCompleted")
    public String orderCompleted(Model model) {
        // Get current user's ID
        Long currentUserId = getCurrentUserId();

        Optional<Cart> optionalCart = cartService.getCartRecent(currentUserId);
        if(!optionalCart.isPresent())
            return "";
        Cart currentCart = optionalCart.get();

        // Replace this with logic to fetch ordered books, e.g., from a database or service
        List<CartItem> orderedBooks = cartItemService.getCurrentOrderedBooks(currentCart);

        List<Book> books = bookService.getBooksByCartItems(orderedBooks);
        // Calculate total price (assuming totalPrice is passed from the payment page)
        double totalPrice = calculateTotalPrice(orderedBooks); // Implement your totalPrice calculation logic

        // Add orderedBooks and totalPrice to the model
        model.addAttribute("books", books);
        model.addAttribute("totalPrice", totalPrice);


        return "orderCompleted"; // Return the view name (orderCompleted.html)
    }

    private double calculateTotalPrice(List<CartItem> orderedBooks) {
        double totalPrice = 0.0;
        for (CartItem cartItem : orderedBooks) {
            totalPrice += cartItem.getBook().getPrice(); // Assuming Book entity has getPrice() method
        }
        return totalPrice;
    }

    // Method to get current user's ID
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Implement logic to fetch current user's ID based on username
        // Example: fetching from a user repository or service
        Users user = usersService.findByUsername(username); // Replace with your actual logic
        return user.getId();
    }
}
