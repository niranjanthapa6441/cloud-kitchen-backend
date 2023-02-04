package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.OrderMenuFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuFoodRepo extends JpaRepository<OrderMenuFood,String> {

}
