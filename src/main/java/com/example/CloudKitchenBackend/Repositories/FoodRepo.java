package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepo extends JpaRepository<Food,Integer> {
    Page<Food> findByName(String name, Pageable paging);
}
