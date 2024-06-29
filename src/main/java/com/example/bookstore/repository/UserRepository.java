package com.example.bookstore.repository;


import com.example.bookstore.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u " +
            "where lower(u.userName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(u.password) like lower(concat('%', :searchTerm, '%'))")
    List<Users> search(@Param("searchTerm") String searchTerm);

    Users findUserById(Long userId);

    Users findByUserName(String userName);
}
