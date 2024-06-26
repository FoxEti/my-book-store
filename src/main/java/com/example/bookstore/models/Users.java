package com.example.bookstore.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Users {

    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;

    private String userName;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Users(String userName, String password, String email) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Users(String email, String password, Role role, String userName) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Users() {
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public enum Role {
        USER,
        ADMIN
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
