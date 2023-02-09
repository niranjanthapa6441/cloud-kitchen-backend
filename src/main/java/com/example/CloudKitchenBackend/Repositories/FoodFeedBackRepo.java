package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.FoodFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodFeedBackRepo extends JpaRepository<FoodFeedback, String> {

}
