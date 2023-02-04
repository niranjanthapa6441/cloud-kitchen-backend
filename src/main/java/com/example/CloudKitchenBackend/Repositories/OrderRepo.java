package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {
}
