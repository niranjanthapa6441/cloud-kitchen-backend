package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, String> {
    Page<Restaurant> findByName(String restaurantName, Pageable paging);
}
