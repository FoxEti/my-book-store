package com.example.bookstore.controller;

import com.example.bookstore.models.Order;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/order")
    public String orderPage(@RequestParam Double totalPrice,Model model) {
        // Initialize necessary attributes if needed
        model.addAttribute("totalPrice", totalPrice); // Example: Set initial totalPrice
        return "order"; // Return the view name (order.html)
    }

    @PostMapping("/processPayment")
    public String processPayment(@RequestParam String address,
                                 @RequestParam Integer phoneNumber,
                                 @RequestParam String shippingType,
                                 @RequestParam Double totalPrice) {

        Order order = new Order();
        //insert values into the DB
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setShippingType(shippingType);
        order.setOrderStatus(Order.OrderStatus.NEW); // Set order status to NEW
        order.setOrderDate(new Date()); // Set current date/time
        order.setTotalPrice(totalPrice);
        //order.setId(null);
        orderService.saveOrder(order); // Save order via service

        return "redirect:/";
    }


}
