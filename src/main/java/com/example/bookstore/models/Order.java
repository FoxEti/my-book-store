package com.example.bookstore.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "\"order\"")
public class Order {

    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long id;

    private Long cartId;
    private Double totalPrice;
    private String address;
    private int phoneNumber;
    private String shippingType;
    private Date orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        NEW,
        PAID,
        SHIPPED,
        COMPLETED
    }

    public Order(Double totalPrice, String address, int phoneNumber, String shippingType, Long cartId, OrderStatus orderStatus, Date orderDate) {
        this.totalPrice = totalPrice;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.shippingType = shippingType;
        this.cartId = cartId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public Order() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getShippingType() {
        return shippingType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


}
