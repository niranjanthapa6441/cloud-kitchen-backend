package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepo extends JpaRepository<Food,Integer> {
}
