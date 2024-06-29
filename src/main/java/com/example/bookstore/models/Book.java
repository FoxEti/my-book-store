package com.example.bookstore.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table
public class Book {

    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "book_sequence"
    )
    private Long id;

    private String title;
    private String author;
    private Double price;
    private String details;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer stockBook;


    public Book(Long id, String imageUrl, String title, String author, Double price, String detailsBook, Category category, Integer stockBook) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.price = price;
        this.details = detailsBook;
        this.category = category;
        this.stockBook = stockBook;
    }


    public Book(String imageUrl, String title, Double price) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
    }

    public Book() {

    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(String imageUrl, String title, String author, Double price, String detailsBook, Category category, Integer stockBook) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.price = price;
        this.details = detailsBook;
        this.category = category;
        this.stockBook = stockBook;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String detailsBook) {
        this.details = detailsBook;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setStockBook(Integer stockBook) {
        this.stockBook = stockBook;
    }

    public Integer getStockBook() {
        return stockBook;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(price, book.price) &&
                Objects.equals(details, book.details) &&
                Objects.equals(imageUrl, book.imageUrl) &&
                Objects.equals(category, book.category) &&
                Objects.equals(stockBook, book.stockBook) ;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (stockBook != null ? stockBook.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String sb = "Book{" + "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", stockBook=" + stockBook +
                '}';
        return sb;
    }


}

