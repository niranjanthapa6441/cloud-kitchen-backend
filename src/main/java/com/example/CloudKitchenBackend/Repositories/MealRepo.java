package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepo extends JpaRepository<Meal, Integer> {

}
