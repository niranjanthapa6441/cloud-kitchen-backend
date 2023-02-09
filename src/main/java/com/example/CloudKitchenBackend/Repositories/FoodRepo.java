package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Category;
import com.example.CloudKitchenBackend.Model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepo extends JpaRepository<Food,Integer> {
    Page<Food> findByCategory(String category, Pageable paging);

    Page<Food> findByName(String category, Pageable paging);

    Page<Food> findByCategoryAndName(String category, String name, Pageable paging);

    Optional<Category> findByCategory(String category);
}
