package com.example.bookstore.models;

import jakarta.persistence.*;


@Entity
@Table
public class CartItem {
    @Id
    @SequenceGenerator(
            name = "cartItem_sequence",
            sequenceName = "cartItem_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cartItem_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int quantity;

    public CartItem(Book book, int quantity){
        this.book = book;
        this.quantity = quantity;
    }

    public CartItem() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


}
