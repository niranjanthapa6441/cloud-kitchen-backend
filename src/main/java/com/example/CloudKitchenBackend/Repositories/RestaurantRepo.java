package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, String> {
}
