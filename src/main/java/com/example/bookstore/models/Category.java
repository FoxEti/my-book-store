package com.example.bookstore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public Category() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
