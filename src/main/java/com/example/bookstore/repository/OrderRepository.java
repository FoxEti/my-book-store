package com.example.bookstore.repository;



import com.example.bookstore.models.Category;
import com.example.bookstore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.orderStatus = :status")
    Order findByOrderStatus(@Param("status") Order.OrderStatus status);


}
