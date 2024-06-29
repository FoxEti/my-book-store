package com.example.bookstore.services;

import com.example.bookstore.models.Order;
import com.example.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        // You can add more business logic here before saving to repository
        orderRepository.save(order);
    }

    public Order getOrderByStatus(Order.OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }
}