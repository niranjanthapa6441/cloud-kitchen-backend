package com.example.CloudKitchenBackend.Repositories;

import com.example.CloudKitchenBackend.Model.CustomerOrder;
import com.example.CloudKitchenBackend.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuFoodRepo extends JpaRepository<OrderItem,String> {

    List<OrderItem> findOrderByOrder(CustomerOrder customerOrder);
}
