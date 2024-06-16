package com.example.bookstore.models;


import jakarta.persistence.*;

@Entity
public class Orders {
    @Id
    @SequenceGenerator(
            name = "orders_sequence",
            sequenceName = "orders_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orders_sequence"
    )
    private Long id;
    private Long orderDetailsId;
    private Long bookId;
    private Double totalPrice;
    private String address;
    private int phoneNumber;
    private String shippingType;

    public Orders(Long orderDetailsId, Long bookId, Double totalPrice, String address, int phoneNumber, String shippingType){
        this.orderDetailsId = orderDetailsId;
        this.bookId = bookId;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.shippingType = shippingType;

    }

    public Orders() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
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
}
