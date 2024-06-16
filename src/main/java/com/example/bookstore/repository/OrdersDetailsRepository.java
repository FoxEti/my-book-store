package com.example.bookstore.repository;


import com.example.bookstore.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
