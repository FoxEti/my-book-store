package com.example.bookstore.models;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class OrderDetails {
    @Id
    @SequenceGenerator(
            name = "orderdetails_sequence",
            sequenceName = "orderdetails_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orderdetails_sequence"
    )
    private Long id;
    private Date dateOrder;
    private Long bookId;
    private int quantity;

    public OrderDetails(Long bookId, Date dateOrder, int quantity){
        this.bookId = bookId;
        this.dateOrder = dateOrder;
        this.quantity = quantity;

    }

    public OrderDetails() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }
}
