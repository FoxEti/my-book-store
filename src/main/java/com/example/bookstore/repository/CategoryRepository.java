package com.example.bookstore.repository;



import com.example.bookstore.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Category findCategoryByName(@Param("categoryName") String categoryName);
}
